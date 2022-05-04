package sk.umb.fpv.dain142demo.iam;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@ToString(exclude = "password")
@AllArgsConstructor
@NoArgsConstructor
public class UserCreate {

    private String username;

    private String password;

    private List<String> roles = new ArrayList();

}
