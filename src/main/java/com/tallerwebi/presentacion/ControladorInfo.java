package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorInfo    {

    @RequestMapping(path="/info")
    public ModelAndView irAInfo(){
        return new ModelAndView("info");
    }


    @RequestMapping(path = "/info/doman")
    public ModelAndView irAInfoDoman(){ return new ModelAndView("doman");}

    @RequestMapping(path = "/info/montessori", method = RequestMethod.GET)
    public ModelAndView irAInfoMontessori() {
        return new ModelAndView("montessori");
    }

    @RequestMapping(path = "/info/waldorf", method = RequestMethod.GET)
    public ModelAndView irAInfoWaldorf() { return new ModelAndView("waldorf");}
}
