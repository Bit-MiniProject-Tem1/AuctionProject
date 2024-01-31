package com.bit.auction.user.service.impl;

import com.bit.auction.user.dto.InquiryDTO;
import com.bit.auction.user.dto.InquiryFileDTO;
import com.bit.auction.user.entity.Inquiry;
import com.bit.auction.user.entity.InquiryFile;
import com.bit.auction.user.repository.InquiryFileRepository;
import com.bit.auction.user.repository.InquiryRepository;
import com.bit.auction.user.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InquiryServiceImpl implements InquiryService {
    private final InquiryRepository inquiryRepository;
    private final InquiryFileRepository inquiryFileRepository;


    @Override
    public void insertInquiry(InquiryDTO inquiryDTO) {
        if (inquiryDTO.getInquiryTitle().equals("")) {
            throw new RuntimeException("title cannot be null");
        }

        if (inquiryDTO.getInquiryContent().equals("")) {
            throw new RuntimeException("content cannot be null");
        }

        Inquiry inquiry = inquiryDTO.toEntity();

        List<InquiryFile> inquiryFileList = inquiryDTO.getInquiryFileDTOList().stream()
                .map(inquiryFileDTO -> inquiryFileDTO.toEntity(inquiry))
                .toList();

        inquiryFileList.stream().forEach(
                inquiryFile -> inquiry.addInquiryFileList(inquiryFile)
        );
        inquiryRepository.save(inquiry);
    }

    @Override
    public InquiryDTO getInquiry(Long inquiryNo) {
        Optional<Inquiry> optionalInquiry = inquiryRepository.searchOne(inquiryNo);

        if (optionalInquiry.isEmpty()){
            throw new RuntimeException("data not exist");
        }

        return optionalInquiry.get().toDTO();
    }

    @Override
    public void updateInquiry(InquiryDTO inquiryDTO, List<InquiryFileDTO> uFileList) {
        Inquiry inquiry = inquiryRepository.findById(inquiryDTO.getInquiryNo()).orElseThrow();

        inquiryDTO.setInquiryRegdate(inquiry.getInquiryRegdate());

        Inquiry updateInquiry = inquiryDTO.toEntity();

        List<InquiryFile> inquiryFileList = uFileList.stream()
                .map(inquiryFileDTO -> inquiryFileDTO.toEntity(updateInquiry))
                .toList();

        inquiryFileList.stream()
                .forEach(
                        inquiryFile -> {
                            if (inquiryFile.getInquiryFileStatus().equals("U") || inquiryFile.getInquiryFileStatus().equals("I")) {
                                updateInquiry.addInquiryFileList(inquiryFile);
                            } else if (inquiryFile.getInquiryFileStatus().equals("D")) {
                                inquiryFileRepository.delete(inquiryFile);
                            }
                        }
                );
        inquiryRepository.saveOne(updateInquiry);
    }

    @Override
    public void deleteInquiry(Long inquiryNo) {
        Inquiry dInquiry = inquiryRepository.findById(inquiryNo).get();
        inquiryRepository.deleteOne(dInquiry);
    }


    @Override
    public Page<InquiryDTO> getInquiryList(Pageable pageable, InquiryDTO inquiryDTO) {
        Page<Inquiry> inquiryPageList = inquiryRepository.searchAll(pageable, inquiryDTO);

        Page<InquiryDTO> inquiryDTOPageList = inquiryPageList.map(inquiry -> inquiry.toDTO());


        return inquiryDTOPageList;
    }



    @Override
    public Page<InquiryDTO> findAll(Pageable pageable) {
        Page<Inquiry> inquiryList = inquiryRepository.findAll(pageable);

        Page<InquiryDTO> inquiryDTOList = inquiryList.map(inquiry -> inquiry.toDTO());

        return inquiryDTOList;
    }

    @Override
    public void save(InquiryDTO inquiryDTO) {
        Inquiry inquiry = inquiryDTO.toEntity();

        List<InquiryFile> inquiryFileList = inquiryDTO.getInquiryFileDTOList().stream()
                .map(inquiryFileDTO -> inquiryFileDTO.toEntity(inquiry))
                .toList();

        inquiryFileList.stream()
                            .forEach(inquiryFile -> inquiry.addInquiryFileList(inquiryFile));

        inquiryRepository.save(inquiry);

    }

    @Override
    public InquiryDTO findById(Long inquiryNo) {
        return inquiryRepository.findById(inquiryNo).get().toDTO();
    }

    @Override
    public InquiryDTO findByInquiryTitle(String inquiryTitle) {

        return inquiryRepository.findByInquiryTitle(inquiryTitle).get().toDTO();
    }

    @Override
    public InquiryDTO findByInquiryTitleAndInquiryContent(String inquiryTitle, String inquiryContent) {
        return inquiryRepository.findByInquiryTitleAndInquiryContent(inquiryTitle, inquiryContent).get().toDTO();
    }

    @Override
    public List<InquiryDTO> findByInquiryTitleContaining(String searchKeyword) {
        return null;
    }

    @Override
    public List<InquiryDTO> search(String searchKeyword, String searchCondition) {
        List<InquiryDTO> inquiryDTOList = new ArrayList<>();
        return inquiryDTOList;
    }

    @Override
    public void updateInquiryCnt(Long inquiryNo) {
        inquiryRepository.updateByInquiryNo(inquiryNo);

    }

    @Override
    public void saveInquiryList(List<Map<String, String>> changeRowsList) {
        changeRowsList.stream()
                .forEach(inquiryMap -> {
                    if(inquiryMap.get("boardStatus").equals("I")) {
                        Inquiry iInquiry = Inquiry.builder()
                                .inquiryType(inquiryMap.get("inquiryType"))
                                .inquiryTitle(inquiryMap.get("inquiryTitle"))
                                .inquiryContent("")
                                .inquiryAnswer(inquiryMap.get(""))
                                .inquiryRegdate(LocalDateTime.now())
                                .inquiryCnt(Integer.parseInt(inquiryMap.get("inquiryCnt")))
                                .inquiryFileList(new ArrayList<>())
                                .build();

                        inquiryRepository.save(iInquiry);
                    } else if(inquiryMap.get("inquiryStatus").equals("U")) {
                        System.out.println("inquiryTitle======================>" + inquiryMap.get("inquiryTitle"));
                        Inquiry uinquiry = Inquiry.builder()
                                .inquiryNo(Long.parseLong(inquiryMap.get("inquiryNo")))
                                .inquiryType(inquiryMap.get("inquiryType"))
                                .inquiryTitle(inquiryMap.get("inquiryTitle"))
                                .inquiryContent("")
                                .inquiryAnswer(inquiryMap.get(""))
                                .inquiryRegdate(LocalDateTime.now())
                                .inquiryCnt(0)
                                .build();

                        inquiryRepository.updateInquiryByInquiryNo(uinquiry);
                    } else if(inquiryMap.get("inquiryStatus").equals("D")) {
                        inquiryRepository.deleteById(Long.parseLong(inquiryMap.get("inquiryNo")));
                    }
                });
    }


}
