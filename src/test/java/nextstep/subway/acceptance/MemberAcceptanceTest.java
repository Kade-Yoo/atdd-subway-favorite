package nextstep.subway.acceptance;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static nextstep.subway.acceptance.MemberSteps.*;
import static org.assertj.core.api.Assertions.assertThat;

class MemberAcceptanceTest extends AcceptanceTest {
    public static final String EMAIL = "email@email.com";
    public static final String PASSWORD = "password";
    public static final int AGE = 20;

    private ExtractableResponse<Response> createResponse;
    private ExtractableResponse<Response> response;

    @BeforeEach
    public void setUp() {
        super.setUp();

        createResponse = 회원_생성_요청(EMAIL, PASSWORD, AGE);
    }

    @DisplayName("회원 정보를 관리한다")
    @Test
    void 회원_정보_관리_통합인수테스트() {
        // When 회원 생성을 요청 (setUp)
        // Then 회원 생성됨
        assertThat(createResponse.statusCode()).isEqualTo(HttpStatus.CREATED.value());

        // When 회원 정보 조회 요청
        response = 회원_정보_조회_요청(createResponse);
        // Then 회원 정보 조회됨
        회원_정보_조회됨(response, EMAIL, AGE);

        // When 회원 정보 수정 요청
        response = 회원_정보_수정_요청(createResponse, "new" + EMAIL, "new" + PASSWORD, AGE);
        // Then 회원 정보 수정됨
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

        // When
        response = 회원_삭제_요청(createResponse);
        // Then 회원 삭제됨
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("회원가입을 한다.")
    @Test
    void createMember() {
        // when
        ExtractableResponse<Response> response = 회원_생성_요청(EMAIL+"new", PASSWORD, AGE);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("회원 정보를 조회한다.")
    @Test
    void getMember() {
        // when
        ExtractableResponse<Response> response = 회원_정보_조회_요청(createResponse);

        // then
        회원_정보_조회됨(response, EMAIL, AGE);

    }

    @DisplayName("회원 정보를 수정한다.")
    @Test
    void updateMember() {
        // when
        ExtractableResponse<Response> response = 회원_정보_수정_요청(createResponse, "new" + EMAIL, "new" + PASSWORD, AGE);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("회원 정보를 삭제한다.")
    @Test
    void deleteMember() {
        // when
        ExtractableResponse<Response> response = 회원_삭제_요청(createResponse);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("회원 정보를 관리한다.")
    @Test
    void manageMember() {
        // When 회원 정보 수정 요청
        response = 회원_정보_수정_요청(createResponse, "new" + EMAIL, "new" + PASSWORD, AGE);
        // Then 회원 정보 수정됨
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("나의 정보를 관리한다.")
    @Test
    void manageMyInfo() {
        // When
        response = 회원_삭제_요청(createResponse);
        // Then 회원 삭제됨
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}