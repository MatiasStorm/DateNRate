package easyon.dating.app.controllers;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.ResultSet;

@Controller
public class DemoController {
    private final JdbcTemplate jdbcTemplate;

    public DemoController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
//        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY)");
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("name", jdbcTemplate.queryForObject("Select * from test where navn = 'torben'", (ResultSet r, int rowCount) -> {
            if(rowCount > 0){
                return r.getString("navn");
            }
            return "Null";
        }));
        return "index";
    }
}
