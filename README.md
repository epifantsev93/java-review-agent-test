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
- write Java test files
- run PMD static analysis
- run Maven tests
- continue multi-step tool calls inside a single agent loop
- keep conversation context between requests using Spring AI ChatMemory
- apply tool call limits
- log agent requests, responses and tool usage

Current flow:

```text
User → Spring AI → LLM → Tool Call → Java Tool → Tool Result → LLM → Response