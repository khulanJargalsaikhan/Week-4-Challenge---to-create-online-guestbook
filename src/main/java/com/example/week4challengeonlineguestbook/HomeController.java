package com.example.week4challengeonlineguestbook;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;

@Controller
public class HomeController {
    public static long idCounter = 0;

    ArrayList<Post> allposts = new ArrayList<>();

    @GetMapping("/")
    public String loadHome(){
        return "home";
    }

    @RequestMapping("/addpost")
    public String showAddpost(Model model){
        Post newPost = new Post();
        idSetter(newPost);
        model.addAttribute("post", newPost);
        return "addpost";
    }


    @PostMapping("/post")
    public String loadPost(@Valid Post post, BindingResult result){
        if (result.hasErrors()){
            return "addpost";
        }else {
            allposts.add(post);
            return "post";
        }
    }


    @RequestMapping("/allposts")
    public String showAllposts(Model model){
        model.addAttribute("allposts", allposts );
        return "allposts";
    }

    @RequestMapping("/post/{id}")
    public String loadDetail(@PathVariable("id") long id, Model model){
        for (Post post: allposts){
            if(id == post.getId()){
                model.addAttribute("post", post);
            }
        }
        return "post";
    }

    @RequestMapping("/update/{id}")
    public String updatePost(@PathVariable("id") long id, Model model){
        for (Post post: allposts){
            if(id == post.getId()){
                allposts.remove(post);
                model.addAttribute("post", post);
                break;
            }
        }
        return "addpost";
    }

    @RequestMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") long id, Model model){
        for (Post post: allposts){
            if(id == post.getId()){
                allposts.remove(post);
                break;
            }
        }
        return "delete";
    }



    public void idSetter(Post post){
        idCounter += 1;
        post.setId(idCounter);
    }
}
