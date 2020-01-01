package com.icecoldbier.tags;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.IOException;

public class EllipsizeTag extends BodyTagSupport {
    private int maxLength;

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int doAfterBody() {

        BodyContent bc = getBodyContent();
        String body = bc.getString();
        JspWriter out = bc.getEnclosingWriter();
        String ellipsizedBody;
        if (body != null) {
            if(body.length() > maxLength){
                ellipsizedBody = body.substring(0, maxLength - 3) + "...";
            }else {
                ellipsizedBody = body;
            }

            try {
                out.print(ellipsizedBody);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return SKIP_BODY;
    }
}