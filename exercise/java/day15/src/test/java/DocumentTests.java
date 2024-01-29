import document.DocumentTemplateType;
import document.RecordType;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.approvaltests.combinations.CombinationApprovals.verifyAllCombinations;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DocumentTests {

    @Test
    void given_glpp_and_individual_prospect_should_return_glpp() {
        final var result = DocumentTemplateType.fromDocumentTypeAndRecordType("GLPP", "INDIVIDUAL_PROSPECT");
        assertThat(result).isEqualTo(DocumentTemplateType.GLPP);
    }

    @Test
    void given_glpp_and_legal_prospect_should_fail() {
        assertThrows(IllegalArgumentException.class,
                () -> DocumentTemplateType.fromDocumentTypeAndRecordType("GLPP", "LEGAL_PROSPECT"));
    }

    @Test
    void given_spec_and_all_should_return_spec() {
        final var result = DocumentTemplateType.fromDocumentTypeAndRecordType("SPEC", "ALL");
        assertThat(result).isEqualTo(DocumentTemplateType.SPEC);
    }

    @Test
    void combinationTests() {
        verifyAllCombinations(
                DocumentTemplateType::fromDocumentTypeAndRecordType,
                Arrays.stream(DocumentTemplateType.values()).map(Enum::name).toArray(String[]::new),
                Arrays.stream(RecordType.values()).map(Enum::name).toArray(String[]::new)
        );
    }


}
