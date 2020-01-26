package com.icecoldbier.tags;
import com.icecoldbier.persistence.entities.Paziente;
import com.icecoldbier.persistence.entities.SSP;
import com.icecoldbier.persistence.entities.User;
import org.apache.commons.text.StringEscapeUtils;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.IOException;

public class MiniProfileTag extends BodyTagSupport {
    private User user;
    private Paziente paziente;
    private SSP ssp;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Paziente getPaziente() {
        return paziente;
    }

    public void setPaziente(Paziente paziente) {
        this.paziente = paziente;
    }

    public SSP getSsp() {
        return ssp;
    }

    public void setSsp(SSP ssp) {
        this.ssp = ssp;
    }

    @Override
    public int doStartTag() {
        try{
            JspWriter out = pageContext.getOut();
            //Tag config
            out.print("<a data-toggle='popover' data-container='body' data-trigger='hover' data-placement='auto' title='Profilo' data-html='true' data-content=\"");

            //If user profile
            if(user != null){
                printRowWithDivider(out, "Tipo", user.getTyp().name());
                printRowWithDivider(out, "Nome", user.toStringNomeCognome());
                printRowWithDivider(out, "Email", user.getUsername());
                printRow(out, "Provincia", user.getProvinciaAppartenenza());
                out.print("\" ><u>" + escape(user.toStringNomeCognome()) + "</u></a>");
            }
            //If paziente profile
            else if(paziente != null){
                //Foto
                out.print("<div class='row'>");
                out.print("<div class='col-12 text-center'>");
                if(paziente.getFoto() == null || paziente.getFoto().trim().equals("")){
                    out.print("<img class='profile-picture-popover' src='/images/profile_placeholder.svg'>");
                }else{
                    out.print("<img class='profile-picture-popover'src='" + escape(paziente.getFotoThumb())+"'/>");
                }
                out.print("</div>");
                out.print("</div>");

                printRowWithDivider(out, "Nome", paziente.toStringNomeCognome());
                printRowWithDivider(out, "Email", paziente.getUsername());
                printRowWithDivider(out, "Provincia", paziente.getProvinciaAppartenenza());
                printRowWithDivider(out, "Data di nascita", paziente.getDataNascita().toString());
                printRowWithDivider(out, "Luogo di nascita", paziente.getLuogoNascita());
                printRowWithDivider(out, "Codice fiscale", paziente.getCodiceFiscale());
                printRowWithDivider(out, "Sesso", "" + paziente.getSesso());
                printRow(out, "Medico base", paziente.getMedico().toStringNomeCognome());

                out.print("\" ><u>" + escape(paziente.toStringNomeCognome()) + "</u></a>");


            }
            //If ssp profile
            else if(ssp != null){
                printRowWithDivider(out, "Tipo", "SSP");
                printRowWithDivider(out, "Nome", ssp.toString());
                printRowWithDivider(out, "Email", ssp.getUsername());
                printRow(out, "Provincia", ssp.getProvinciaAppartenenza());

                out.print("\" ><u>" + escape(ssp.toString()) + "</u></a>");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return TagSupport.EVAL_PAGE;
    }

    private String escape(String unescapedString){
        return StringEscapeUtils.escapeHtml4(unescapedString);
    }

    private void printRow(JspWriter out, String heading, String content) throws IOException {
        out.print("<div class='row'>");
        out.print("<div class='col-6'>");
        out.print("<b>" + escape(heading) + "</b>");
        out.print("</div>");
        out.print("<div class='col-6'>");
        out.print(escape(content));
        out.print("</div>");
        out.print("</div>");
    }

    private void printRowWithDivider(JspWriter out, String heading, String content) throws IOException {
        printRow(out, heading, content);
        out.print("<div class='divider'></div>");
    }
}