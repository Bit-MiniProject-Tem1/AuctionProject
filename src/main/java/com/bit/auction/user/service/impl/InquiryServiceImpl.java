package com.bit.auction.user.service.impl;

import com.bit.auction.user.dto.InquiryDTO;
import com.bit.auction.user.entity.Inquiry;
import com.bit.auction.user.entity.InquiryFile;
import com.bit.auction.user.repository.InquiryFileRepository;
import com.bit.auction.user.repository.InquiryRepository;
import com.bit.auction.user.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InquiryServiceImpl implements InquiryService {
    private final InquiryRepository inquiryRepository;


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
    public List<InquiryDTO> findAll() {
        List<Inquiry> inquiryList = inquiryRepository.findAll();

        List<InquiryDTO> inquiryDTOList = new ArrayList<>();

        for (Inquiry inquiry : inquiryList) {
            inquiryDTOList.add(InquiryDTO.toinquiryDTO(inquiry));

        }

        return inquiryDTOList;
    }
}
