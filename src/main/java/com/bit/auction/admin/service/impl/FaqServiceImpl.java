package com.bit.auction.admin.service.impl;

import com.amazonaws.services.kms.model.NotFoundException;
import com.bit.auction.admin.dto.FaqAttachedFileDTO;
import com.bit.auction.admin.dto.FaqDTO;
import com.bit.auction.admin.entity.Faq;
import com.bit.auction.admin.entity.FaqAttachedFile;
import com.bit.auction.admin.repository.FaqAttachedFileRepository;
import com.bit.auction.admin.repository.FaqRepository;
import com.bit.auction.admin.service.FaqService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class FaqServiceImpl implements FaqService {

    private final EntityManager entityManager;
    private final FaqRepository faqRepository;

    private final FaqAttachedFileRepository faqAttachedFileRepository;

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
    public void updateFaq(FaqDTO faqDTO) {

        Faq newFaq = faqDTO.toEntity();

        Optional<Faq> optionalFaq = faqRepository.findById(faqDTO.getFaqId());

        Faq originFaq = optionalFaq.get();

        List<FaqAttachedFile> faqAttachedFileList = faqDTO.getFaqAttachedFileDTOList().stream()
                .map(faqAttachedFileDTO -> faqAttachedFileDTO.toEntity(newFaq)).toList();

        originFaq.setCategory(faqDTO.getCategory());
        originFaq.setTitle(faqDTO.getTitle());
        originFaq.setContent(faqDTO.getContent());
        originFaq.setRegdate(LocalDateTime.now());
        originFaq.setViewsCount(faqDTO.getViewsCount());
        originFaq.setFaqAttachedFileList(faqAttachedFileList);

        faqAttachedFileRepository.deleteByFaq(newFaq);

        entityManager.persist(originFaq);
    }

    @Override
    public void updateFaq(Long faqId, FaqDTO faqDTO) {

        Faq originFaq = faqRepository.findById(faqId)
                .orElseThrow(() -> new NotFoundException("Faq not found with ID: " + faqId));

        faqAttachedFileRepository.deleteByFaq(originFaq);

        faqAttachedFileRepository.flush();

        updateFaqDTO(originFaq, faqDTO);

        faqRepository.save(originFaq);
    }

    private void updateFaqDTO(Faq originFaq, FaqDTO faqDTO) {
        originFaq.setCategory(faqDTO.getCategory());
        originFaq.setTitle(faqDTO.getTitle());
        originFaq.setContent(faqDTO.getContent());

        List<FaqAttachedFile> attachedFiles = faqDTO.getFaqAttachedFileDTOList().stream()
                .map(faqAttachedFileDTO -> faqAttachedFileDTO.toEntity(originFaq))
                .collect(Collectors.toList());
        originFaq.setFaqAttachedFileList(attachedFiles);
    }

    @Override
    public void deleteFaq(Long faqId) {
        Faq faq = faqRepository.findById(faqId).get();

        if(faq != null) {
            faqRepository.deleteById(faqId);
        }
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
    public Faq save(Faq faq) {
        entityManager.persist(faq);
        return faq;
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
    public FaqDTO findTopByOrderByFaqIdDesc() {
        return faqRepository.findTopByOrderByFaqIdDesc().toDTO();
    }


    @Override
    public List<Faq> findAllByFaqId(FaqDTO faqDTO) {
        Faq faq = faqDTO.toEntity();
        String jpql = "select f from FaqAttachedFile f where faq = :faq";

        return null;
    }

    @Transactional
    @Override
    public void deleteById(Long faqId) {
        faqRepository.deleteById(faqId);
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

}
