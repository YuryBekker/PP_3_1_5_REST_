import model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Main {
    static String URL = "http://94.198.50.185:7081/api/users";
    final static RestTemplate restTemplate = new RestTemplate();
    private static final HttpHeaders headers = new HttpHeaders();

    public static void main(String[] args) {
        HttpEntity<User> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Cookie", response.getHeaders().get("Set-Cookie").get(0));
        System.out.println(response.getBody());

        User userJames = new User(3L, "James", "Brown", (byte) 18);
        User userThomas = new User(3L, "Thomas", "Shelby", (byte) 1);
        String answer = method(userJames, HttpMethod.POST) +
                method(userThomas, HttpMethod.PUT) +
                delete(userThomas);

        System.out.println(answer);

    }

    private static String delete(User user) {
        URL += "/" + user.getId();
        return method(user, HttpMethod.DELETE);
    }

    private static String method(User user, HttpMethod method) {
        HttpEntity<User> http = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(URL, method, http, String.class);
        return response.getBody();
    }
}
