package moe.src.leyline.framework.interfaces.view.decorator;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.IContext;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dialect.springdata.decorator.FullPaginationDecorator;

/**
 * Created by bytenoob on 6/9/16.
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.google.auto.service.AutoService;

import org.springframework.data.domain.Page;
import org.thymeleaf.Arguments;
import org.thymeleaf.dialect.springdata.Keys;
import org.thymeleaf.dialect.springdata.decorator.PaginationDecorator;
import org.thymeleaf.dialect.springdata.util.Messages;
import org.thymeleaf.dialect.springdata.util.PageUtils;
import org.thymeleaf.dialect.springdata.util.Strings;
import org.thymeleaf.dom.Element;

import static org.thymeleaf.dialect.springdata.util.Strings.AND;
import static org.thymeleaf.dialect.springdata.util.Strings.EMPTY;
import static org.thymeleaf.dialect.springdata.util.Strings.EQ;
import static org.thymeleaf.dialect.springdata.util.Strings.PAGE;
import static org.thymeleaf.dialect.springdata.util.Strings.Q_MARK;

@Component
@AutoService(PaginationDecorator.class)
public final class LeylineFullPaginationDecorator implements PaginationDecorator {
    private static final String DEFAULT_CLASS="pagination";
    private static final String BUNDLE_NAME = org.thymeleaf.dialect.springdata.decorator.FullPaginationDecorator.class.getSimpleName();
    private static final int DEFAULT_PAGE_SPLIT = 7;
    public static final String SLASH="/";
    public static final String HTML=".html";

    public String getIdentifier() {
        return "full-static";
    }

    public final String decorate(Element element, Arguments arguments) {

        Page<?> page = PageUtils.findPage(arguments);

        //laquo
        String firstPage = createPageUrl(arguments, 0);
        boolean isFirstPage = page.getNumber()==0;
        Locale locale = arguments.getContext().getLocale();
        String laquo = isFirstPage ? getLaquo(locale) : getLaquo(firstPage, locale);

        //Previous page
        String previous = getPreviousPageLink(page, arguments);

        //Links
        String pageLinks = createPageLinks(page, arguments);

        //Next page
        String next = getNextPageLink(page, arguments);

        //raquo
        boolean isLastPage = page.getTotalPages()==0 || page.getNumber()==(page.getTotalPages()-1);
        String lastPage = createPageUrl(arguments, page.getTotalPages()-1);
        String raquo = isLastPage ? getRaquo(locale) : getRaquo(lastPage, locale);

        boolean isUl = Strings.UL.equals(element.getNormalizedName());
        String currentClass = element.getAttributeValue(Strings.CLASS);
        String clas = (isUl && !Strings.isEmpty(currentClass)) ? currentClass : DEFAULT_CLASS;

        return Messages.getMessage(BUNDLE_NAME, "pagination", locale, clas, laquo, previous, pageLinks, next, raquo);
    }

    private String createPageLinks(final Page<?> page, final Arguments arguments){
        int pageSplit = DEFAULT_PAGE_SPLIT;
        if( arguments.hasLocalVariable(Keys.PAGINATION_SPLIT_KEY) ){
            pageSplit = (Integer) arguments.getLocalVariable(Keys.PAGINATION_SPLIT_KEY);
        }

        int firstPage=0;
        int latestPage=page.getTotalPages();
        int currentPage = page.getNumber();
        if( latestPage>=pageSplit ){
            //Total pages > than split value, create links to split value
            int pageDiff = latestPage - currentPage;
            if(currentPage==0){
                //From first page to split value
                latestPage = pageSplit;
            }else if( pageDiff < pageSplit ){
                //From split value to latest page
                firstPage = currentPage - (pageSplit - pageDiff);
            }else{
                //From current page -1 to split value
                firstPage = currentPage - 1;
                latestPage = currentPage + pageSplit - 1;
            }
        }

        //Page links
        StringBuilder builder = new StringBuilder();
        for (int i = firstPage; i < latestPage; i++) {
            int pageNumber = i+1;
            String link = createPageUrl(arguments, i);
            boolean isCurrentPage = i==currentPage;
            Locale locale = arguments.getContext().getLocale();
            String li = isCurrentPage ? getLink(pageNumber, locale) : getLink(pageNumber, link, locale);
            builder.append(li);
        }

        return builder.toString();
    }

    private String getLaquo(Locale locale){
        return Messages.getMessage(BUNDLE_NAME, "laquo", locale);
    }

    private String getLaquo(String firstPage, Locale locale) {
        return Messages.getMessage(BUNDLE_NAME, "laquo.link", locale, firstPage);
    }

    private String getRaquo(Locale locale){
        return Messages.getMessage(BUNDLE_NAME, "raquo", locale);
    }

    private String getRaquo(String lastPage, Locale locale) {
        return Messages.getMessage(BUNDLE_NAME, "raquo.link", locale, lastPage);
    }

    private String getLink(int pageNumber, Locale locale){
        return Messages.getMessage(BUNDLE_NAME, "link.active", locale, pageNumber);
    }

    private String getLink(int pageNumber, String url, Locale locale){
        return Messages.getMessage(BUNDLE_NAME, "link", locale, url, pageNumber);
    }

    private String getPreviousPageLink(Page<?> page, final Arguments arguments) {
        int previousPage = page.getNumber()-1;
        String msgKey = previousPage<0 ? "previous.page" : "previous.page.link";
        Locale locale = arguments.getContext().getLocale();
        String link = createPageUrl(arguments, previousPage);

        return Messages.getMessage(BUNDLE_NAME, msgKey, locale, link);
    }

    private String getNextPageLink(Page<?> page, final Arguments arguments) {
        int nextPage = page.getNumber()+1;
        int totalPages = page.getTotalPages();
        String msgKey = nextPage==totalPages ? "next.page" : "next.page.link";
        Locale locale = arguments.getContext().getLocale();
        String link = createPageUrl(arguments, nextPage);

        return Messages.getMessage(BUNDLE_NAME, msgKey, locale, link);
    }

    public static String createPageUrl(final Arguments arguments, int pageNumber){
        String prefix = getParamPrefix(arguments);
        final Collection<String> excludedParams = Arrays.asList(new String[]{prefix.concat(PAGE)});
        final String baseUrl = buildBaseUrl(arguments, excludedParams).replaceAll("(\\/page\\/\\d*)+(\\.html|.)","");

        return buildUrl(baseUrl, pageNumber , arguments).toString();
    }

    public static StringBuilder pageNumURLBuilder(int pageNumber){
        return new StringBuilder(SLASH).append(PAGE).append(SLASH).append(pageNumber).append(HTML);
    }

    private static String getParamPrefix(Arguments arguments){
        String prefix = (String) arguments.getLocalVariable(Keys.PAGINATION_QUALIFIER_PREFIX);

        return prefix==null ? EMPTY : prefix.concat("_");
    }

    private static String buildBaseUrl(final Arguments arguments, Collection<String> excludeParams){
        //URL defined with pagination-url tag
        final String url = (String) arguments.getLocalVariable(Keys.PAGINATION_URL_KEY);

        final IContext context = arguments.getContext();
        if( url==null && context instanceof IWebContext){
            //Creates url from actual request URI and parameters
            final StringBuilder builder = new StringBuilder();
            final IWebContext webContext = (IWebContext) context;
            final HttpServletRequest request = webContext.getHttpServletRequest();

            //URL base path from request
            builder.append(request.getRequestURI());

            Map<String, String[]> params = request.getParameterMap();
            Set<Map.Entry<String, String[]>> entries = params.entrySet();
            boolean firstParam = true;
            for (Map.Entry<String, String[]> param : entries) {
                //Append params not excluded to basePath
                String name = param.getKey();
                if( !excludeParams.contains(name) ){
                    if(firstParam){
                        builder.append(Q_MARK);
                        firstParam=false;
                    }else{
                        builder.append(AND);
                    }

                    //Iterate over all values to create multiple values for parameter
                    String[] values = param.getValue();
                    Collection<String> paramValues = Arrays.asList(values);
                    Iterator<String> it = paramValues.iterator();
                    while ( it.hasNext() ) {
                        String value = it.next();
                        builder.append(name).append(EQ).append(value);
                        if( it.hasNext() ){
                            builder.append(AND);
                        }
                    }
                }
            }

            return builder.toString();
        }

        return url==null ? EMPTY : url;
    }


    private static StringBuilder buildUrl(String baseUrl, int pageNumber,Arguments arguments){
        String paramAppender = String.valueOf(baseUrl).contains(Q_MARK) ? AND : "";
        String prefix = getParamPrefix(arguments);

        return new StringBuilder(baseUrl).append(pageNumURLBuilder(pageNumber)).append(paramAppender).append(prefix);
    }

}
