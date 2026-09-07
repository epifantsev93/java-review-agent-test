# Java Review Agent

Pet project for learning AI agents with Java and Spring AI.

The idea is to build a small code review agent that can inspect a Java project using tools instead of relying only on the LLM context.

## Current state

The agent can:

- communicate with a local LLM through Ollama
- use Spring AI tool calling
- discover Java files in the project
- search Java code with line-level evidence
- read project files
- continue multi-step tool calls inside a single agent loop
- keep conversation context between requests using Spring AI ChatMemory
- apply tool call limits
- log agent requests, responses and tool usage

Current flow:

```text
User → Spring AI → LLM → Tool Call → Java Tool → Tool Result → LLM → Response
```

Conversation history is kept in memory for the current application session.

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

Add test execution and agent evaluation, then gradually introduce richer evidence, audit capabilities and asynchronous task execution.