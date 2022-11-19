package net.gabor7d2.pcbuilderold;

import net.gabor7d2.pcbuilder.gui.MainFrame;

public class Main {
    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();


        /*WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);

        HtmlPage page = client.getPage("https://www.arukereso.hu/alaplap-c3128/?start=0");
        List<HtmlElement> list = page.getByXPath("//div[@class='product-box-container clearfix']");

        for (HtmlElement e : list) {
            HtmlElement name = e.getFirstByXPath(".//div[@class='name ulined-link']");
            if (name != null) {
                System.out.println(name.getVisibleText());
            }
            HtmlElement price = e.getFirstByXPath(".//div[@class='price']");
            if (price != null) {
                System.out.println(price.getVisibleText());
            }
        }*/
    }
}