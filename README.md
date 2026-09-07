# Java Review Agent

Small pet project for learning AI-agent development with Java and Spring AI.

The project implements a simple code review agent that can inspect a Java project through tools instead of relying only on the LLM context.

The main goal is to explore agentic workflows, tool calling and basic Harness concepts in practice.

## Stack

* Java 25
* Spring Boot
* Spring AI
* Ollama
* Qwen3 8B
* Maven
* PMD

## What the agent can do

The agent can:

* communicate with a local LLM through Ollama
* use Spring AI tool calling
* discover Java source files
* search Java code and return file paths, line numbers and matching lines
* read project files
* run PMD static analysis
* run Maven tests
* create or overwrite Java test files inside `src/test/java`
* perform multiple tool calls within a single agent workflow
* keep conversation context using Spring AI `ChatMemory`
* apply limits to tool calls
* log agent requests, responses and tool usage

## Basic flow

```text
User
  ↓
Spring AI ChatClient
  ↓
LLM
  ↓
Tool Call
  ↓
ReviewAgentTools
  ↓
ProjectFileService
  ↓
Filesystem / Maven / PMD
  ↓
Tool Result
  ↓
LLM
  ↓
Response
```

## Harness-related constraints

The project contains several basic execution constraints:

* tool call limits
* file path validation
* test file writes restricted to `src/test/java`
* Maven command timeout
* Maven output size limit
* graceful and forced process termination
* cross-platform Maven Wrapper execution

These are intentionally lightweight and are meant to demonstrate the idea of controlling agent execution through the surrounding environment rather than relying only on prompts.

## Example scenarios

The agent can be asked to:

```text
Find usages of ProcessBuilder and explain whether they are safe.
```

```text
Review path handling in this project and provide file and line evidence.
```

```text
Run PMD and explain only actual PMD violations.
```

```text
Inspect AgentConfigCalculator and create tests for it.
```

## Running locally

Requirements:

* Java 25
* Docker
* Ollama

Start Ollama:

```bash
docker compose up -d
```

Pull the model if it is not available yet:

```bash
ollama pull qwen3:8b
```

Run the application on Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Run tests:

```bash
./mvnw test
```

or on Windows:

```powershell
.\mvnw.cmd test
```

## Current limitations

This is a learning project, not a production-ready agent runtime.

Current limitations include:

* generated tests are executed on the host and are not sandboxed
* conversation memory currently uses a single application-level conversation
* project workspace is based on the application's working directory
* file reading and search results do not yet have full context-budget limits
* test execution is available to the model but is not enforced as a mandatory verification gate
* there is no benchmark/eval suite yet

These limitations are useful discussion points for further development of the Harness layer.
