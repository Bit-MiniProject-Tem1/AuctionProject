package com.bit.auction.user.service.impl;

import com.bit.auction.user.dto.FaqAttachedFileDTO;
import com.bit.auction.user.dto.FaqDTO;
import com.bit.auction.user.entity.Faq;
import com.bit.auction.user.entity.FaqAttachedFile;
import com.bit.auction.user.repository.FaqAttachedFileRepository;
import com.bit.auction.user.repository.FaqRepository;
import com.bit.auction.user.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FaqServiceImpl implements FaqService {

    private final FaqRepository faqRepository;

    private final FaqAttachedFileRepository faqAttachedFileRepository;
/*
    @PersistenceContext
    public EntityManager entityManager;
//    private Object ;

    public Page<Faq> test(Pageable pageable, String category, String searchKeyword) {
        String query = "SELECT * FROM FAQ " +
                        "WHERE CATEGORY = '" + category + "'" +
                        "  AND (TITLE LIKE '%" + searchKeyword + "%' OR CONTENT LIKE '%" + searchKeyword + "%')";

        List resultList = entityManager.createNativeQuery(query, Object.class).getResultList();
        resultList.stream().map(obj -> faqRepository.findById(obj.getField("faqId")));

        return convertListToPage(resultList, pageable);
    }
*/

/*
    public Page<Faq> convertListToPage(List<Faq> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : (start + pageable.getPageSize());

        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }
*/

    @Override
    public void insertFaq(FaqDTO faqDTO) {
        faqDTO.setRegdate(LocalDateTime.now().toString());

        if(faqDTO.getCategory().equals("")) {
            throw new RuntimeException("category cannot be null");
        }

        if(faqDTO.getTitle().equals("")) {
            throw new RuntimeException("title cannot be null");
        }

        if(faqDTO.getContent().equals("")) {
            throw new RuntimeException("content cannot be null");
        }

        Faq faq = faqDTO.toEntity();

        List<FaqAttachedFile> faqAttachedFileList = faqDTO.getFaqAttachedFileDTOList().stream()
                        .map(faqAttachedFileDTO -> faqAttachedFileDTO.toEntity(faq)).toList();

        faqAttachedFileList.stream().forEach(faqAttachedFile -> faq.addFaqAttachedFileList(faqAttachedFile));

        faqRepository.save(faq);
    }

    @Override
    public FaqDTO getFaq(Long faqId) {
        return null;
    }

    @Override
    public FaqDTO updateViewsCount(Long faqId) {
        Optional<Faq> faq = faqRepository.findById(faqId);
        faq.get().setViewsCount(faq.get().getViewsCount() + 1);
        return faqRepository.save(faq.get()).toDTO();
    }

    @Override
    public void updateFaq(FaqDTO faqDTO, List<FaqAttachedFileDTO> uFileList) {

    }

    @Override
    public void deleteFaq(Long faqId) {

    }

    @Override
    public Page<FaqDTO> getFaqList(Pageable pageable, FaqDTO faqDTO) {
        Page<Faq> faqPageList = faqRepository.findAll(pageable);
        String category = faqDTO.getCategory();
        String condition = faqDTO.getSearchCondition();
        String keyword = faqDTO.getSearchKeyword();

        if(keyword != null && !keyword.equals("")){
            if(category.equals("전체")){
                if(condition.equals("전체")) {
                    faqPageList = faqRepository.findByTitleContainingOrContentContaining(
                            pageable, keyword, keyword);
                } else if(condition.equals("제목")) {
                    faqPageList = faqRepository.findByTitleContaining(
                            pageable, keyword);
                } else if(condition.equals("내용")) {
                    faqPageList = faqRepository.findByContentContaining(
                            pageable, keyword);
                }
            } else {
                if(condition.equals("전체")) {
                    /*faqPageList = faqRepository.findByCategoryAndTitleContainingOrContentContaining(
                            pageable, category, keyword, keyword);*/
                    faqPageList = faqRepository.findByCategoryAndTitleContainingOrCategoryAndContentContaining(pageable, category, keyword, category, keyword);
                } else if(condition.equals("제목")) {
                    faqPageList = faqRepository.findByCategoryAndTitleContaining(
                            pageable, category, keyword);
                } else if(condition.equals("내용")) {
                    faqPageList = faqRepository.findByCategoryAndContentContaining(
                            pageable, category, keyword);
                }
            }
        } else {
            if(category != null && !category.equals("전체")){
                faqPageList = faqRepository.findByCategory(pageable, category);
            }
        }

        return faqPageList.map(faq -> faq.toDTO());
    }

/*
    @Override
    public List<FaqAttachedFileDTO> getFaqAttachedFileList(Long faqId) {

        return faqAttachedFileRepository.findByFaqId(faqId.toString());
    }
*/


/*
    @Override
    public Page<FaqDTO> findAll(Pageable pageable) {
        Page<Faq> faqList = faqRepository.findAll(pageable);

        Page<FaqDTO> faqDTOList = faqList.map(faq -> faq.toDTO());

        return faqDTOList;
    }
*/
    @Override
    public Page<FaqDTO> findAll(Pageable pageable) {
        return faqRepository.findAll(pageable).map(faq -> faq.toDTO());
    }

    @Override
    public void save(FaqDTO faqDTO) {
        Faq faq = faqDTO.toEntity();

        List<FaqAttachedFile> faqAttachedFileList = faqDTO.getFaqAttachedFileDTOList().stream()
                .map(faqAttachedFileDTO -> faqAttachedFileDTO.toEntity(faq))
                .toList();

        faqAttachedFileList.stream()
                .forEach(faqAttachedFile -> faq.addFaqAttachedFileList(faqAttachedFile));

        faqRepository.save(faq);
    }

    @Override
    public FaqAttachedFileDTO getFaqAttachedFileDTO(FaqAttachedFileDTO faqAttachedFileDTO) {
        return faqAttachedFileRepository.findByFileIdAndFaqFaqId(faqAttachedFileDTO.getFileId(), faqAttachedFileDTO.getFaqId()).orElseThrow().toDTO();
    }

    @Override
    public FaqAttachedFileDTO getFaqAttachedFileDTO(Long faqId, Long fileId) {
        return faqAttachedFileRepository.findByFaqFaqIdAndFileId(faqId, fileId).toDTO();
    }


    @Override
    public FaqDTO findById(Long faqId) {
        return faqRepository.findById(faqId).get().toDTO();
    }

    @Override
    public List<FaqDTO> findByCategory(Pageable pageable, String category) {
        return null;
    }

    @Override
    public List<FaqDTO> findByTitleContaining(String searchKeyword) {
        return null;
    }

    @Override
    public List<FaqDTO> findByContentContaining(String searchKeyword) {
        return null;
    }

    @Override
    public List<FaqDTO> findByCategoryAndTitleContaining(String category, String searchKeyword) {
        return null;
    }

    @Override
    public List<FaqDTO> findByCategoryAndContentContaining(String category, String searchKeyword) {
        return null;
    }

    @Override
    public List<FaqDTO> findByCategoryAndTitleContainingOrContentContaining(String category, String searchKeyword) {
        return null;
    }

    @Override
    public List<FaqDTO> search(String category, String searchCondition, String searchKeyword) {
        return null;
    }

/*    @Override
    public List<FaqAttachedFileDTO> findByFaqId(Long faqId) {
        return null;
    }*/
}
