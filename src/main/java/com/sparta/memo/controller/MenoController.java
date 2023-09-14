package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MenoController {
    //Key,Value
    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping("/memos") //Create
    public MemoResponseDto createMeno(@RequestBody MemoRequestDto requetDto) {
        //RequestDto > Entity
        Memo memo = new Memo(requetDto);

        //Memo Max id 찾기
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId);

        //DB 저장
        memoList.put(memo.getId(), memo);

        //Entity > ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    @GetMapping("/memos")//Read
    public List<MemoResponseDto> getMemos() {
        // Map To List
        List<MemoResponseDto> responselist = memoList.values().stream().map(MemoResponseDto::new).toList();

        return responselist;
    }

    @PutMapping("/memos/{id}")//Update
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        if (memoList.containsKey(id)) {
            // 메모 가져오기
            Memo memo = memoList.get(id);

            // 메모 수정
            memo.update(requestDto);
            return memo.getId();
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/memos/{id}")//Delete
    public Long deleteMemo(@PathVariable Long id) {
        // 해당 메모가 DB에 존재하는지 확인
        if (memoList.containsKey(id)) {
            // 메모 삭제하기
            memoList.remove(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }


}
