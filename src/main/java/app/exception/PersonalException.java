package app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class PersonalException extends RuntimeException{

    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pb.setTitle("PicPay internal server error");
        return pb;
    }
}
