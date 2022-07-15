package com.luoluo.mt.excell.rule;

import com.luoluo.mt.excell.ValidResult;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2021-04-19 9:53
 * @remark: 请添加类说明
 */
public abstract class AbstractValidRule implements ValidRule {

    /** 验证的列--哪一列从0开始 **/
    protected Integer colStart;

    /** 验证的列的个数  **/
    protected Integer colSum;

    /** 失败的说明 **/
    protected String failDesc;

    /** 和下一个验证有关联 true 有关系 false 没有关系 **/
    private Boolean relatedNext;

    public AbstractValidRule() {
        this(null, null, null);
    }

    public AbstractValidRule(Integer colStart, Integer colSum, String failDesc) {
        this.colStart = colStart;
        this.colSum = colSum;
        this.failDesc = failDesc;
        this.relatedNext = false;
    }

    public AbstractValidRule(Integer colStart, Integer colSum, String failDesc, Boolean relatedNext) {
        this(colStart, colSum, failDesc);
        this.relatedNext = relatedNext;
    }

    @Override
    public Integer validStart() {
        return colStart;
    }

    @Override
    public Integer validSum() {
        return colSum;
    }

    @Override
    public String validFailDesc() {
        return failDesc;
    }


    public void setColStart(Integer colStart) {
        this.colStart = colStart;
    }

    public void setColSum(Integer colSum) {
        this.colSum = colSum;
    }

    public void setFailDesc(String failDesc) {
        this.failDesc = failDesc;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-04-19 14:12:57
     * @remark 和下一个验证使用同一个验证结果
     */
    public Boolean getRelatedNextValue() {
        return relatedNext;
    }

    /**
     * 定义个统一严重的 入口 template 默认处理, self 可能需要自己来实现处理（不管如何子类都必须实现 validOneNotNullData）
     * 如果子类重写了这个方法，那么可能 validOneNotNullData 这个方法不会被使用生效（当重写方法里面没有调用的时候）
     *
     * 注意：一般情况下 子类 不需要去重写 validProcess！！，这会让父类的模板方法 validProcess 失效了！！！
     * validOneNotNullData 方法被调用的时机：
     *
     * **/
    @Override
    public  void validProcess(ValidResult validResult) {
        // 如果验证失败就不再验证
        if (validResult.getStatus() == false){
            return;
        }
        List<String> dataList = validResult.getColDataList();
        for (int i = 0; i < dataList.size(); i++){
            String data = dataList.get(i);
            if (StringUtils.isNotBlank(data) && validOneNotNullData(data, i) == false){
                setFailInfo(validResult);
                return;
            }
        }
    }

    // 默认的验证单元格 规则方式：必须给实现的！！！
//    public abstract void validProcessSelf(ValidResult validResult);

    /**
     * @Author: lijiangguo
     * @Date: 2021-04-19 11:30:08
     * @remark 设置错误的描述
     */
    protected void setFailInfo(ValidResult validResult) {
        String desc = validResult.getInvalidDesc();
        if (StringUtils.isBlank(desc)){
            validResult.setInvalidDesc(validFailDesc());
        }else {
            validResult.setInvalidDesc(desc + "," + validFailDesc());
        }
        validResult.setStatus(false);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-04-19 11:30:08
     * @remark 设置错误的描述
     */
    protected void setFailInfo(ValidResult validResult, String msg) {
        setFailInfo(validResult);
        if (StringUtils.isBlank(validResult.getInvalidDesc())){
            validResult.setInvalidDesc(msg);
        }else {
            validResult.setInvalidDesc(validResult.getInvalidDesc() + "," + msg);
        }
        validResult.setStatus(false);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021-04-19 13:24:04
     * @remark 对每一个的验证
     */
    protected abstract boolean validOneNotNullData(String data, int index);
}
