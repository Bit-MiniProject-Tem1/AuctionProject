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
    public void updateFaq(FaqDTO faqDTO) {
        log.info("#### faqId = {}", faqDTO.getFaqId());

        // 1. FaqDTO를 이용하여 Faq 엔티티를 가져옴
        Faq newFaq = faqDTO.toEntity();
        log.info("#### newFaq.getFaqId = {}", newFaq.getFaqId());

        // 2. 데이터베이스에서 해당 faqId에 해당하는 기존 Faq 엔티티를 조회
        Optional<Faq> optionalFaq = faqRepository.findById(faqDTO.getFaqId());

        Faq originFaq = optionalFaq.get();
        log.info("#### originFaq.getFaqId = {}", originFaq.getFaqId());

        // 3.첨부파일을 엔티티로 변환
        List<FaqAttachedFile> faqAttachedFileList = faqDTO.getFaqAttachedFileDTOList().stream()
                .map(faqAttachedFileDTO -> faqAttachedFileDTO.toEntity(newFaq)).toList();

        log.info("----------------------- File Name -----------------------------");
        for(int i = 0; i<faqAttachedFileList.size(); i++) {

            log.info("#### originFaq.getFaqId = {}", originFaq.getFaqId());
        }
        log.info("---------------------------------------------------------------");

        log.info("##### 변경전 엔티티 - 제목 : {}", originFaq.getTitle());

        // 4. 기존 Faq 엔티티의 필드들을 FaqDTO의 값으로 업데이트
        originFaq.setCategory(faqDTO.getCategory());
        originFaq.setTitle(faqDTO.getTitle());
        originFaq.setContent(faqDTO.getContent());
        originFaq.setRegdate(LocalDateTime.now());
        originFaq.setViewsCount(faqDTO.getViewsCount());
        originFaq.setFaqAttachedFileList(faqAttachedFileList);

        log.info("##### 변경후 엔티티 - 제목 : {}", originFaq.getTitle());


        // 5. 기존 FaqAttachedFile 엔티티 삭제
        faqAttachedFileRepository.deleteByFaq(newFaq);
/*

        // 파일 레포지토리에 파일 추가
        faqAttachedFileList.stream().forEach(faqAttachedFile -> updateFaq.addFaqAttachedFileList(faqAttachedFile));
*/

        // 6. 데이터베이스에 업데이트된 Faq 엔티티를 저장
//        faqRepository.save(originFaq);
        entityManager.persist(originFaq);
    }

    @Override
    public void updateFaq(Long faqId, FaqDTO faqDTO) {
/*
        Faq originFaq = entityManager.find(Faq.class, faqId);


        List<FaqAttachedFile> faqAttachedFileList = faqDTO.getFaqAttachedFileDTOList().stream()
                .map(faqAttachedFileDTO -> faqAttachedFileDTO.toEntity(faqDTO.toEntity())).toList();

        originFaq.setCategory(faqDTO.getCategory());
        originFaq.setTitle(faqDTO.getTitle());
        originFaq.setContent(faqDTO.getContent());
        originFaq.setRegdate(LocalDateTime.now());
        originFaq.setViewsCount(faqDTO.getViewsCount());
        originFaq.setFaqAttachedFileList(faqAttachedFileList);
*/

/*
        Optional<Faq> faq = faqRepository.findById(faqId);
        faq.get().setViewsCount(faq.get().getViewsCount() + 1);
        return faqRepository.save(faq.get()).toDTO();
*/




        Faq originFaq = faqRepository.findById(faqId)
                .orElseThrow(() -> new NotFoundException("Faq not found with ID: " + faqId));

        // 업데이트를 수행할 수 있는 메소드를 호출 (아래에 구현)
        updateFaqDTO(originFaq, faqDTO);

        faqRepository.save(originFaq);
    }

    private void updateFaqDTO(Faq originFaq, FaqDTO faqDTO) {
        originFaq.setCategory(faqDTO.getCategory());
        originFaq.setTitle(faqDTO.getTitle());
        originFaq.setContent(faqDTO.getContent());
        // 그 외 필요한 필드 업데이트

        // 첨부 파일 업데이트
        List<FaqAttachedFile> attachedFiles = faqDTO.getFaqAttachedFileDTOList().stream()
                .map(faqAttachedFileDTO -> faqAttachedFileDTO.toEntity(originFaq))
                .collect(Collectors.toList());
        originFaq.setFaqAttachedFileList(attachedFiles);
    }

    @Override
    public void deleteFaq(Long faqId) {

        Faq faq = faqRepository.findById(faqId).get();
        if (faq != null) {
//            entityManager.remove(faq);
            faqRepository.delete(faq);
        }
    }

    @Override
    public void delete(FaqDTO faqDTO) {

//        Faq faq =  faqRepository.delete(faqDTO);

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

//        List<Faq> faqAttachedFileList = entityManager.createQuery(jpql, faqDTO.class).getResultList();

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

/*    @Override
    public List<FaqAttachedFileDTO> findByFaqId(Long faqId) {
        return null;
    }*/
}
