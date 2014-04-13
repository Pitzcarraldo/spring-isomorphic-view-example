package com.spring.mustache.controller;

import com.spring.mustache.model.Item;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("home");
        mav.addObject("message", "Hello, Mustache!");
        mav.addObject("items", getItems(1, 3));
        return mav;
    }

    @RequestMapping(value = "/items/{from}/{to}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Collection> items(@PathVariable Integer from, @PathVariable Integer to) {
        Map<String, Collection> map = new HashMap<String, Collection>();
        map.put("items", getItems(from, to));
        return map;
    }

    private Collection<Item> getItems(Integer from, Integer to) {
        List<Item> items = new ArrayList<Item>();
        for (int i = from; i <= to; i++) {
            items.add(new Item("item" + i, "content" + i));
        }
        return items;
    }
}