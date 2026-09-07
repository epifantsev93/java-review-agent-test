package agent.review;

public class AgentConfigCalculator {

    private static final int BASE_TOOL_CALLS = 3;
    private static final int FILES_PER_EXTRA_CALL = 5;
    private static final int MAX_TOOL_CALLS = 20;

    public int calculateMaxToolCalls(int javaFileCount) {
        if (javaFileCount < 0) {
            throw new IllegalArgumentException(
                    "javaFileCount must be non-negative"
            );
        }

        int extraCalls = (int) Math.ceil(
                (double) javaFileCount / FILES_PER_EXTRA_CALL
        );

        return Math.min(
                MAX_TOOL_CALLS,
                BASE_TOOL_CALLS + extraCalls
        );
    }
}