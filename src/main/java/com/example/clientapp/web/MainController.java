package com.example.clientapp.web;

import com.example.clientapp.domain.Client;
import com.example.clientapp.domain.Rol;
import com.example.clientapp.domain.UserApp;
import com.example.clientapp.services.ClientService;
import com.example.clientapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String index(Model model) {
        List<Client> customers = clientService.findAll();
        Double total = customers
                .stream()
                .map(client -> client.getCredit())
                .reduce(0.0, (acc, x) -> acc + x);
        model.addAttribute("customers", customers);
        model.addAttribute("totalCustomers", customers.size());
        model.addAttribute("total", new DecimalFormat("$#,###,##0.00").format(total));
        return "index";
    }

    @PostMapping("/client/new")
    public String addNewClient(Client client) {
        clientService.save(client);
        return "redirect:/";
    }

    @GetMapping("/client/{idClient}/edit")
    public String getEditClientForm(@PathVariable Long idClient, Model model) {
        Client client = clientService.findById(idClient);
        model.addAttribute("client", client);
        return "client/edit";
    }

    @PostMapping("/client/{idClient}/edit")
    public String editClient(Client client) {
        clientService.save(client);
        return "redirect:/";
    }

    @GetMapping("/client/remove")
    public String removeClient(@RequestParam Long clientId) {
        Client client = new Client();
        client.setId(clientId);
        clientService.remove(client);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String getRegisterForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(UserApp user, @RequestParam(name = "role", defaultValue = "") String isAdmin) {
        List<Rol> roles = new ArrayList();
        Rol userRole = new Rol();
        userRole.setName("ROLE_USER");
        userRole.setUser(user);
        roles.add(userRole);
        if (!isAdmin.equals("")) {
            Rol adminRol = new Rol();
            adminRol.setName("ROLE_ADMIN");
            adminRol.setUser(user);
            roles.add(adminRol);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        userService.save(user);
        return "redirect:/";
    }
}
