package zero;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Entry point
 * Created by pallav.kothari on 7/9/17.
 */
@RestController
@SpringBootApplication
public class Echo {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();

    @RequestMapping("/")
    public String index(@RequestHeader HttpHeaders headers, @RequestHeader(name="gap-auth") String gapAuth, @RequestHeader(name="gap-signature") String gapSignature) {
        System.out.println("headers = " + headers);
        System.out.println("gapAuth = " + gapAuth);
        System.out.println("gapSignature = " + gapSignature);
        HashFunction hf = Hashing.hmacSha1("secret0".getBytes(Charsets.UTF_8));
        HashCode hash = hf.newHasher()
                .putString(gapAuth, Charsets.UTF_8)
                .hash();
        System.out.println("hash = " + BaseEncoding.base64().encode(hash.asBytes()));
        return "<pre>" + gson.toJson(headers) + "</pre>";
    }

    public static void main(String[] args) {
        SpringApplication.run(Echo.class, args);
    }
}
