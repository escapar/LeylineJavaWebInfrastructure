package moe.src.leyline.framework.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonView;
import moe.src.leyline.framework.interfaces.view.AppView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 分页查询的结果Page类不支持序列化,给它包一层
 */
public class PageJSON<T> extends org.springframework.data.domain.PageImpl<T> {
    public PageJSON(final List<T> content, final Pageable pageable, final long total) {
        super(content, pageable, total);
    }

    public PageJSON(final List<T> content) {
        super(content);
    }

    public PageJSON(final Page<T> page, final Pageable pageable) {
        super(page.getContent(), pageable, page.getTotalElements());
    }

    @JsonView(AppView.LIST.class)
    public int getTotalPages() {
        return super.getTotalPages();
    }

    @JsonView(AppView.LIST.class)
    public long getTotalElements() {
        return super.getTotalElements();
    }

    @JsonView(AppView.LIST.class)
    public boolean hasNext() {
        return super.hasNext();
    }

    @JsonView(AppView.LIST.class)
    public boolean isLast() {
        return super.isLast();
    }

    @JsonView(AppView.LIST.class)
    public boolean hasContent() {
        return super.hasContent();
    }

    @JsonView(AppView.LIST.class)
    public List<T> getContent() {
        return super.getContent();
    }
}
