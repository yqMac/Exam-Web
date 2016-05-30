package com.yqmac.exam.auth;

/**
 * 权限枚举，用于验证用户操作的权限管理
 * Created by yqmac on 2016/5/20 0020.
 */
public enum EnumAuth {
    Base(0),
    UnLogined(-1),
    Logined(1),
    QuesBankM(9),
    QuesLibM(8),
    QuesM(7),
    UserM(10),
    RightM(11),
    RoleM(12),
    ExamM(13),
    ClassM(16),
    Personal(20),
    QuesTypeM(22),
    QuesPoint(23),
    EnteredExam(24),
    HandleAnswer(26),
    Statistics(27);

    /**
     * 返回整型值对应的枚举
     * @param rightId
     * @return
     */
    public static EnumAuth valueof(int rightId) {

        switch (rightId) {
            case 0:
                return Base;
            case -1:
                return UnLogined;
            case 1:
                return Logined;
            case 9:
                return QuesBankM;
            case 8:
                return QuesLibM;
            case 7:
                return QuesM;
            case 10:
                return UserM;
            case 11:
                return RightM;
            case 12:
                return RoleM;
            case 13:
                return ExamM;
            case 16:
                return ClassM;
            case 20:
                return Personal;
            case 22:
                return QuesTypeM;
            case 23:
                return QuesPoint;
            case 24:
                return EnteredExam;
            case 26:
                return HandleAnswer;
            case 27:
                return Statistics;
            default:
                return null;
        }
    }

    /**
     * 返回枚举对应的整型值
     * @return
     */
    public int vlaue(){
        return this.rightId;
    }



    private int rightId;

    private EnumAuth(int _rightId) {
        this.rightId = _rightId;
    }


    @Override
    public String toString() {
        return super.toString() + " " + this.rightId;
    }
}
