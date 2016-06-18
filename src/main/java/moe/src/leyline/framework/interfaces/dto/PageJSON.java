package moe.src.leyline.framework.interfaces.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import moe.src.leyline.framework.interfaces.view.LeylineView;

/**
 * Created by bytenoob on 6/10/16.
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

    @JsonView(LeylineView.LIST.class)
    public int getTotalPages() {
        return super.getTotalPages();
    }

    @JsonView(LeylineView.LIST.class)
    public long getTotalElements() {
        return super.getTotalElements();
    }

    @JsonView(LeylineView.LIST.class)
    public boolean hasNext() {
        return super.hasNext();
    }

    @JsonView(LeylineView.LIST.class)
    public boolean isLast() {
        return super.isLast();
    }

    @JsonView(LeylineView.LIST.class)
    public boolean hasContent() {
        return super.hasContent();
    }

    @JsonView(LeylineView.LIST.class)
    public List<T> getContent() {
        return super.getContent();
    }
}
