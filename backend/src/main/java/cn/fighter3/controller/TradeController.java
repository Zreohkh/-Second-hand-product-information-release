package cn.fighter3.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.fighter3.dto.QueryDTO;
import cn.fighter3.dto.applyDTO;
import cn.fighter3.result.Result;
import cn.fighter3.service.TradeService;

@RestController
@RequestMapping("/api/trade")
public class TradeController {
    @Autowired
    private TradeService tradeService;
    @PostMapping("/apply")
    public Result applyFor(@RequestBody applyDTO applyDTO){
        return new Result(200,tradeService.insertTrade(applyDTO),null);
    }
    @PostMapping("/appliedlist")
    public Result tradeList(@RequestBody QueryDTO queryDTO){
        return new Result(200,"",tradeService.selectAppliedPage(queryDTO));
    }

    @PostMapping("/deleteApplied")
    public Result deleteApplied(Integer id){
        return new Result(200,"",tradeService.deleteTrade(id));
    }

    @PostMapping("/deleteApplied/batch")
    public Result batchDeleteApplied(@RequestBody List<Integer> ids){
        return new Result(200,"",tradeService.batchDelete(ids));
    }

    @PostMapping("/applylist")
    public Result applyList(@RequestBody QueryDTO queryDTO){
        return new Result(200,"",tradeService.selectApplyPage(queryDTO));
    }
}
