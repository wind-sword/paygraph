package whu.graph.pay.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Pagecontroller {
	@RequestMapping(value="/sa")
	public String saPage() throws IOException  {

		return "page/searchAccount.html";
	}
	@RequestMapping(value="/sc")
	public String scPage() throws IOException  {

		return "page/searchCompany.html";
	}
	@RequestMapping(value="/ud")
	public String upPage() throws IOException  {

		return "page/admin.html";
	}
}
