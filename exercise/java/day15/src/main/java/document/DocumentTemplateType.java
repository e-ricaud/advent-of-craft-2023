package document;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum DocumentTemplateType {
    DEERPP("DEER", RecordType.INDIVIDUAL_PROSPECT),
    DEERPM("DEER", RecordType.LEGAL_PROSPECT),
    AUTP("AUTP", RecordType.INDIVIDUAL_PROSPECT),
    AUTM("AUTM", RecordType.LEGAL_PROSPECT),
    SPEC("SPEC", RecordType.ALL),
    GLPP("GLPP", RecordType.INDIVIDUAL_PROSPECT),
    GLPM("GLPM", RecordType.LEGAL_PROSPECT);

    private final String documentType;
    private final RecordType recordType;

    DocumentTemplateType(String documentType, RecordType recordType) {
        this.documentType = documentType;
        this.recordType = recordType;
    }

    private static Map<String, DocumentTemplateType> mergedMapping = Stream.concat(
                    List.of(DocumentTemplateType.values()).stream().collect(Collectors
                            .toMap(v -> formatKey(v.getDocumentType(), v.getRecordType().name()), v -> v)).entrySet().stream(),
                    List.of(RecordType.values()).stream()
                            .collect(Collectors.toMap(v -> formatKey(SPEC.name(), v.name()), v -> SPEC)).entrySet().stream()
            )
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (existing, replacement) -> existing));


    private static String formatKey(String documentType, String recordType) {
        return documentType.toUpperCase() + "-" + recordType.toUpperCase();
    }

    public static DocumentTemplateType fromDocumentTypeAndRecordType(String documentType, String recordType) {
        return Optional.ofNullable(
                mergedMapping.get(formatKey(documentType, recordType))
        ).orElseThrow(() -> new IllegalArgumentException("Invalid Document template type or record type"));
    }

    private RecordType getRecordType() {
        return recordType;
    }

    private String getDocumentType() {
        return documentType;
    }
}