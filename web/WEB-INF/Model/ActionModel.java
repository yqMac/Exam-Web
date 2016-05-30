package com.yqmac.exam.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.yqmac.exam.service.IRightService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/4/20 0020.
 */
@Controller
public class RightAction implements ModelDriven<TRight> {

    public String add() {
        return SUCCESS;
    }

    public String add() {
        rightService.add(right);
        ActionContext.getContext().put("url","/right_list");
        return "redirect";
    }

    public String updatePre() {

        TRight r = rightService.loadById(rightId);
        copypro(r);
        return SUCCESS;
    }

    public String update() {

        rightService.update(right);

        ActionContext.getContext().put("url","/right_list");
        return "redirect";
    }

    public String delete() {
        rightService.delete(rightId);
        ActionContext.getContext().put("url","/right_list");
        return "redirect";
    }

    public String list() {
        List<TRight>rights = rightService.list();
        ActionContext.getContext().put("rights",rights);
        return SUCCESS;
    }
    

    //基本
    private static final String SUCCESS = "success";
    private void copypro(TRight r){
        right.setId(r.getId());
        right.setRightName(r.getRightName());
        right.setParentId(r.getParentId());
        right.setRightUrl(r.getRightUrl());
    }

    private IRightService rightService;

    @Resource
    public void setRightService(IRightService rightService) {
        this.rightService = rightService;
    }

    private TRight right;

    private int rightId;

    public int getRightId() {
        return rightId;
    }

    public void setRightId(int rightId) {
        this.rightId = rightId;
    }

    public TRight getRight() {
        return right;
    }

    public void setRight(TRight right) {
        this.right = right;
    }

    @Override
    public TRight getModel() {
        if (right == null) {
            right = new TRight();
        }
        return right;
    }
}
