package com.yqmac.exam.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.yqmac.exam.service.IClassService;
import com.yqmac.exam.service.IQuesBankService;
import com.yqmac.exam.vo.TPaperStrategy;
import com.yqmac.exam.vo.TQuesBank;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yqmac on 2016/4/20 0020.
 */
@Controller("testAction")
public class TestAction implements ModelDriven<TQuesBank> {

    private List<TPaperStrategy> sss = new ArrayList<>();

    public List<TPaperStrategy> getSss() {
        return sss;
    }

    public void setSss(List<TPaperStrategy> sss) {
        this.sss = sss;
    }

    public String add(Model model) {
        copypro(new TQuesBank());

        Map<String, String> maps = new HashMap<>();
        maps.put("1", "ooooo");
        ActionContext.getContext().put("maps",maps);
        return SUCCESS;
    }

    public String add() {
        //System.out.println("test的add方法: " + (sss == null ? "null" : sss.size()));
        //for (TPaperStrategy ss : sss) {
        //    System.out.println("数据: "+ss);
        //}
        //quesBankService.add(bank);
        //classService.update(null);
        return null;
    }

    public String updatePre() {
        TQuesBank b = quesBankService.loadById(bank.getId());
        copypro(b);
        return SUCCESS;
    }

    public String update() {
        quesBankService.update(bank);
        return redirect();
    }

    public String delete() {
        quesBankService.delete(bank.getId());
        return redirect();
    }

    public String list() {
        List<TQuesBank> banks = quesBankService.listAll();
        ActionContext.getContext().put("banks", banks);
        return SUCCESS;
    }

    //基本
    private TQuesBank bank;
    private int bankId;
    private IQuesBankService quesBankService;

    @Resource(name = "classService")
    private IClassService classService;



    public IQuesBankService getQuesBankService() {
        return quesBankService;
    }

    @Resource
    public void setQuesBankService(IQuesBankService quesBankService) {
        this.quesBankService = quesBankService;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public TQuesBank getBank() {
        return bank;
    }

    public void setBank(TQuesBank bank) {
        this.bank = bank;
    }

    private static final String SUCCESS = "success";

    private void copypro(TQuesBank b) {
        bank.setId(b.getId());
        bank.setBankName(b.getBankName());
    }

    @Override
    public TQuesBank getModel() {
        if (bank == null) {
            bank = new TQuesBank();
        }
        return bank;
    }

    private String redirect() {
        ActionContext.getContext().put("url", "quesBank_list");
        return "redirect";
    }
}
