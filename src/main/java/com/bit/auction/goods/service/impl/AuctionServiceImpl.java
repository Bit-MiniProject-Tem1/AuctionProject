package com.bit.auction.goods.service.impl;

import com.bit.auction.common.FileUtils;
import com.bit.auction.goods.dto.AuctionDTO;
import com.bit.auction.goods.entity.Auction;
import com.bit.auction.goods.entity.AuctionImg;
import com.bit.auction.goods.entity.Category;
import com.bit.auction.goods.repository.AuctionImgRepository;
import com.bit.auction.goods.repository.AuctionRepository;
import com.bit.auction.goods.repository.CategoryRepository;
import com.bit.auction.goods.repository.LikeCntRepository;
import com.bit.auction.goods.service.AuctionService;
import com.bit.auction.user.entity.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuctionServiceImpl implements AuctionService {
    private final AuctionRepository auctionRepository;
    private final CategoryRepository categoryRepository;
    private final AuctionImgRepository auctionImgRepository;
    private final FileUtils fileUtils;
    private List<Long> categoryIdList = new ArrayList<>();
    private final LikeCntRepository likeCntRepository;

    @Override
    public AuctionDTO getAuctionGoods(Long id) {
        Optional<Auction> optionalAuction = auctionRepository.findById(id);
        return optionalAuction.map(Auction::toDTO).orElse(null);
    }

    public List<Long> getSubCategoryIdList(Long categoryId) {
        categoryIdList = categoryRepository.findSubCategoryIdList(categoryId);
        return categoryIdList;
    }

    @Override
    public Page<AuctionDTO> getAuctionList(Pageable pageable, Long categoryId, String sortOption, List<String> target, List<Character> status) {
        categoryIdList.add(categoryId);
        Page<Auction> auctionPageList = auctionRepository.searchAll(pageable, categoryIdList, sortOption, target, status);
        Page<AuctionDTO> auctionDTOPageList = auctionPageList.map(auction -> auction.toDTO());
        categoryIdList.clear();
        return auctionDTOPageList;
    }

   @Override
    public Page<AuctionDTO> getMyAuctionList(Pageable pageable, String regUserId, List<Character> status) {
        Page<Auction> auctionPageList = auctionRepository.searchMyAuctionList(pageable, regUserId, status);
        Page<AuctionDTO> auctionDTOPageList = auctionPageList.map(auction -> auction.toDTO());

        return auctionDTOPageList;
    }

    @Transactional
    @Override
    public void setAuction(AuctionDTO auctionDTO, Long categoryId, User user) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));

        String stripTitle = auctionDTO.getTitle().strip();
        String stripDescription = auctionDTO.getDescription().strip();

        auctionDTO.setTitle(stripTitle);
        auctionDTO.setDescription(stripDescription);
        auctionDTO.setRegUserId("kim");
        auctionDTO.setStatus('S');

        Auction auction = auctionDTO.toEntity(category, user);

        if (auctionDTO.getAuctionImgDTOList() != null) {
            List<AuctionImg> auctionImgList = auctionDTO.getAuctionImgDTOList().stream()
                    .map(auctionImgDTO -> auctionImgDTO.toEntity(auction))
                    .toList();

            auctionImgList.forEach(auctionImg -> {
                auction.addAuctionImg(auctionImg);
            });

        }

        if (auctionDTO.getDeleteAuctionImgList() != null) {
            List<Long> deleteIdList = auctionDTO.getDeleteAuctionImgList().stream().toList();
            List<AuctionImg> deleteImgList = auctionImgRepository.findAllById(deleteIdList);

            AtomicBoolean isRepresentative = new AtomicBoolean(false);
            deleteImgList.forEach(img -> {
                fileUtils.deleteObject("auction/" + img.getFileName());
                if (img.isRepresentative()) {
                    isRepresentative.set(true);
                }
                auctionImgRepository.deleteById(img.getId());
            });
        }

        auctionRepository.saveOne(auction);
    }

    @Override
    public void removeDescriptionImg(String description, String originDescription, List<String> temporaryImageList) {
        Pattern pattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");
        List<String> imgList = new ArrayList<>();
        Matcher matcher = pattern.matcher(description);
        while (matcher.find()) {
            imgList.add(matcher.group(1));
        }

        if (temporaryImageList != null || temporaryImageList.size() > 0) {
            temporaryImageList.forEach(img -> {
                int count = 0;
                for (int i = 0; i < imgList.size(); i++) {
                    if (imgList.contains(img)) {
                        count++;
                    }
                }
                String imgName = img.replace("https://kr.object.ncloudstorage.com/bitcamp-bucket-122/", "");
                if (count == 0) {
                    fileUtils.deleteObject(imgName);
                }
            });
        }

        if (originDescription != null) {
            List<String> originImgList = new ArrayList<>();
            matcher = pattern.matcher(originDescription);
            while (matcher.find()) {
                originImgList.add(matcher.group(1));
            }

            originImgList.forEach(img -> {
                int count = 0;
                for (int i = 0; i < imgList.size(); i++) {
                    if (imgList.contains(img)) {
                        count++;
                    }
                }
                String imgName = img.replace("https://kr.object.ncloudstorage.com/bitcamp-bucket-122/", "");
                if (count == 0) {
                    fileUtils.deleteObject(imgName);
                }
            });
        }
    }

    @Override
    public void removeDescriptionImg(List<String> temporaryImageList) {
        if (temporaryImageList != null || temporaryImageList.size() > 0) {
            temporaryImageList.forEach(img -> {
                String imgName = img.replace("https://kr.object.ncloudstorage.com/bitcamp-bucket-122/", "");

                fileUtils.deleteObject(imgName);

            });
        }

    }

    @Transactional
    public void updateView(Long id, HttpServletRequest request, HttpServletResponse response) {
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {
                this.auctionRepository.updateView(id);
                oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                response.addCookie(oldCookie);
            }
        } else {
            this.auctionRepository.updateView(id);
            Cookie newCookie = new Cookie("postView", "[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(newCookie);
        }
    }
    @Override
    public void cancelAuction(Long id) {
        auctionRepository.updateStatusByCancel(id);
    }

    @Override
    public Page<AuctionDTO> searchAuctions(Pageable pageable, String searchQuery, List<Character> status) {
        List<Character> statusList = new ArrayList<>();
        if (status != null || !status.isEmpty()) {
            statusList.add('S');
            statusList.addAll(status);
        }

        Page<Auction> auctionPageList = auctionRepository.findByAuctionNameContaining(pageable, searchQuery, statusList);
        Page<AuctionDTO> auctionDTOPageList = auctionPageList.map(auction -> auction.toDTO());

        return auctionDTOPageList;
    }

    public List<AuctionDTO> findByForRecentList() {
        List<Auction> finalAuctions = auctionRepository.findByforResent();
        return finalAuctions.stream()
                .map(Auction::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<AuctionDTO> findByForFinalList() {
        List<Auction> finalAuctions = auctionRepository.findByforFinal();
        return finalAuctions.stream()
                .map(Auction::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Long>> getLikeSumList() {
        return likeCntRepository.countGroupByAuctionId();
    }

    @Override
    public List<Map<String, Long>> getUserLikeList(long id) {
        return likeCntRepository.countGroupByAuctionIdUserId(id);
    }

    @Override
    public List<AuctionDTO> findByForPopularList() {
        List<Auction> popularAcutions = auctionRepository.countGroupByAuctionId();
        return popularAcutions.stream()
                .map(Auction::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuctionDTO> findByUserId(long id) {
        List<Auction> likelist = auctionRepository.findByUserId(id);
        return likelist.stream()
                .map(Auction::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void setCurrentBiddingPrice(Long auctionId, int BiddingPrice) {
        auctionRepository.updateCurrentBiddingPrice(auctionId, BiddingPrice);
    }
}
