package com.bit.auction.user.controller;

import com.bit.auction.goods.dto.PointDTO;
import com.bit.auction.goods.service.AuctionService;
import com.bit.auction.goods.service.PointService;
import com.bit.auction.user.dto.ResponseDTO;
import com.bit.auction.user.dto.UserDTO;
import com.bit.auction.user.entity.CustomUserDetails;
import com.bit.auction.user.entity.User;
import com.bit.auction.user.repository.UserRepository;
import com.bit.auction.user.service.UserService;
import com.bit.auction.user.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final AuctionService auctionService;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final PointService pointService;
    private String findId;




    @GetMapping("/join-view")
    public ModelAndView getJoin() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/login/join.html");

        return mav;
    }



    @GetMapping("/login-view")
    public ModelAndView getLogin(HttpServletRequest request) {
        request.getSession().setAttribute("prevPage", request.getHeader("Referer"));

        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/login/login.html");

        return mav;
    }


    @PostMapping("/id-check")
    public ResponseEntity<?> idCheck(UserDTO userDTO) {
        System.out.println(userDTO.getUserId());
        ResponseDTO<Map<String, String>> response = new ResponseDTO<>();

        Map<String, String> returnMap = new HashMap<>();

        try {
            int idCheck = userService.idCheck(userDTO.getUserId());

            if(idCheck == 0) {
                returnMap.put("idCheckMsg", "idOk");
            } else {
                returnMap.put("idCheckMsg", "idFail");
            }

            response.setItem(returnMap);
            response.setStatusCode(HttpStatus.OK.value());

            return ResponseEntity.ok(response);
        } catch(Exception e) {
            response.setErrorCode(501);
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(response);
        }
    }


    @PostMapping("/join")
    public ModelAndView join(UserDTO userDTO, PointDTO pointDTO) {
        userDTO.setUserPw(passwordEncoder.encode(userDTO.getUserPw()));
        userDTO.setRole("ROLE_USER");
        pointDTO.setUserId(userDTO.getUserId());
        pointDTO.setPoint(0);
        userService.join(userDTO);
        pointService.pointJoin(pointDTO);

        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/login/login.html");

        return mav;
    }

    @GetMapping("/reg-goods")
    public ModelAndView getMyAuction(@RequestParam(required = false) String status,
                                     @PageableDefault(page = 0, size = 10) Pageable pageable,
                                     @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        ModelAndView mav = new ModelAndView();

        String regUserId = customUserDetails.getUsername();

        mav.addObject("auctionList", auctionService.getMyAuctionList(pageable, regUserId, status));
        mav.setViewName("user/mypage/getMyAuctionList.html");

        return mav;
    }

    @GetMapping("/login")
    public ModelAndView login(UserDTO userDTO, HttpSession session, HttpServletRequest request) {
        int idCheck = userService.idCheck(userDTO.getUserId());
        ModelAndView mav = new ModelAndView();

        if (idCheck == 0) {
            mav.addObject("loginFailMsg", "idNotExist");
            mav.setViewName("user/login/login.html");
        } else {
            UserDTO loginUser = userService.login(userDTO);

            if (loginUser == null) {
                mav.addObject("loginFailMsg", "wrongPw");
                mav.setViewName("user/login/login.html");
            } else {
                // 로그인 성공 시의 추가 처리 (예: 세션에 사용자 정보 저장 등)
                session.setAttribute("loggedInUser", loginUser);
                // 리다이렉션 설정
                String uri = request.getHeader("Referer");
                if (uri != null && !uri.contains("/login")) {
                    request.getSession().setAttribute("prevPage", uri);
                }
                // 홈페이지로 리다이렉트
                mav.setViewName("redirect:/");
            }
        }
        return mav;
    }


    @PostMapping("/find_id")
    public String  findId(
            @RequestParam("userName") String userName,
            @RequestParam("userTel") String userTel) {

        Optional<User> userOptional = userService.findId(userName, userTel);

        if (userOptional.isPresent()) {
            findId = userOptional.get().getUserId();
            return "/user/find_id2";
        } else {
            return "아이디를 찾을 수 없습니다. 사용자의 이름과 전화번호를 정확하게 입력해주세요.";
        }

    }

    @GetMapping("/find_id2-view")
    public ModelAndView getFindId2(Model model) {
        ModelAndView mav = new ModelAndView();

        model.addAttribute("userId",findId);

        mav.setViewName("user/login/find_id2.html");

        return mav;
    }

    @GetMapping("/find_id-view")
    public ModelAndView getFindId() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/login/find_id.html");

        return mav;
    }

    @GetMapping("/find_pw-view")
    public ModelAndView getFindPw() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/login/find_pw.html");

        return mav;
    }

    @GetMapping("/find_pw2")
    public ModelAndView getFindPw2() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/login/find_pw2.html");

        return mav;
    }

    @PostMapping("/find_pw")
    public ResponseEntity<?> findPw(@RequestParam String userId,
                                               @RequestParam String userName,
                                               @RequestParam String userTel) {
        Optional<User> userOptional = userService.findPw(userId, userName, userTel);

        String response = "";

        if (userOptional.isPresent()) {
            response = "success";
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/modify-view")
    public ModelAndView getModify() {
        ModelAndView mav = new ModelAndView();

        mav.setViewName("user/mypage/modify.html");

        return mav;
    }


    @PutMapping("/modify")
    public ResponseEntity<?> modify(UserDTO userDTO, HttpSession session) {
        ResponseDTO<Map<String, String>> response = new ResponseDTO<>();

        try {
            userService.modify(userDTO);

            Map<String, String> retunMap = new HashMap<>();

            retunMap.put("msg", "정상적으로 수정되었습니다.");

            response.setItem(retunMap);
            response.setStatusCode(HttpStatus.OK.value());

            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userDTO.getUserId());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );

            SecurityContext securityContext = SecurityContextHolder.getContext();

            securityContext.setAuthentication(authentication);

            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

            return ResponseEntity.ok(response);
        } catch (Exception e){
            if (e.getMessage().equals("wrong password")) {
                response.setErrorCode(900);
            } else {
                response.setErrorCode(901);
            }
            response.setErrorMessage(e.getMessage());
            response.setStatusCode(HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.badRequest().body(response);
        }
     }
    }





