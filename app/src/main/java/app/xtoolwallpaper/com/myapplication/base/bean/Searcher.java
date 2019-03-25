package app.xtoolwallpaper.com.myapplication.base.bean;

public class Searcher<T> {
    private Long startDate;
    private Long endDate;
    private String wd;
    private Integer pageNum;
    private Integer pageSize;
    private T condition;

    public Searcher() {
        this.reset();
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getWd() {
        return wd;
    }

    public void setWd(String wd) {
        this.wd = wd;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public T getCondition() {
        return condition;
    }

    public void setCondition(T condition) {
        this.condition = condition;
    }

    /**
     * 页数自增
     */
    public void pageNumGrow() {
        this.pageNum++;
    }

    /**
     * 重置信息
     */
    public void reset() {
        pageNum = 0;
        wd = "";
        startDate = null;
        endDate = null;
    }
}
