package few.common.audit.model;

import few.utils.ListWrapper;

import java.util.List;

/**
 * User: igor
 * Date: 31.01.12
 */
public abstract class BasePaginator<T> extends ListWrapper<T> {

    private int page;
    private int pageSize;

    public BasePaginator(List<T> ts, int page, int pageSize) {
        super(ts);
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    private Integer recordCount;
    public int getRecordCount() {
        if( recordCount == null )
            recordCount = _recordCount();
        return recordCount;
    }

    abstract protected int _recordCount();

    public int getPageCount() {
        int count = getRecordCount();
        int ret = count / pageSize;
        if( count % pageSize > 0 )
            ++ret;
        return ret;
    }


}
