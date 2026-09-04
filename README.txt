# Java Review Agent

Pet project for learning AI agents with Java and Spring AI.

The idea is to build a small code review agent that can inspect a Java project using tools instead of relying only on the LLM context.

## Current state

The agent can:

- communicate with a local LLM through Ollama
- use Spring AI tool calling
- discover Java files in the project
- return the tool result back to the LLM

Current flow:

```text
User → Spring AI → LLM → Tool Call → Java Tool → LLM → Response
```

## Stack

- Java 25
- Spring Boot 4.1
- Spring AI 2.0
- Ollama
- Qwen3 8B
- Maven

## Run

Start Ollama:

```bash
docker compose up -d
```

Download the model:

```bash
docker exec -it ollama ollama pull qwen3:8b
```

Then run the Spring Boot application and open:

```text
http://localhost:8080
```

## Next steps

Add tools for code search, file reading and test execution, then gradually introduce evidence, guardrails and agent evaluation.