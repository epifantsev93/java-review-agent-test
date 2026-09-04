const conversation = document.getElementById("conversation");
const emptyState = document.getElementById("emptyState");

const promptInput = document.getElementById("promptInput");
const sendButton = document.getElementById("sendButton");

const exampleButtons = document.querySelectorAll(".example-button");

function hideEmptyState() {
    if (emptyState) {
        emptyState.style.display = "none";
    }
}

function addMessage(role, content, extraClass = "") {

    hideEmptyState();

    const message = document.createElement("div");

    message.className = `message ${role} ${extraClass}`;

    const header = document.createElement("div");
    header.className = "message-header";

    header.textContent =
        role === "user"
            ? "YOU"
            : "AGENT";

    const body = document.createElement("div");
    body.className = "message-content";
    body.textContent = content;

    message.appendChild(header);
    message.appendChild(body);

    conversation.appendChild(message);

    window.scrollTo({
        top: document.body.scrollHeight,
        behavior: "smooth"
    });

    return message;
}

function addLoadingMessage() {

    hideEmptyState();

    const message = document.createElement("div");
    message.className = "message agent";

    message.innerHTML = `
        <div class="message-header">AGENT</div>

        <div class="message-content">
            <div class="loading">
                <span></span>
                <span></span>
                <span></span>
            </div>
        </div>
    `;

    conversation.appendChild(message);

    return message;
}

async function sendPrompt() {

    const prompt = promptInput.value.trim();

    if (!prompt) {
        return;
    }

    addMessage("user", prompt);

    promptInput.value = "";
    resizeTextarea();

    sendButton.disabled = true;

    const loadingMessage = addLoadingMessage();

    try {

        const response = await fetch("/api/chat", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                userInput: prompt
            })
        });

        if (!response.ok) {
            throw new Error(
                `HTTP ${response.status}: ${response.statusText}`
            );
        }

        const result = await response.text();

        loadingMessage.remove();

        addMessage("agent", result);

    } catch (error) {

        loadingMessage.remove();

        addMessage(
            "agent",
            `Request failed: ${error.message}`,
            "error"
        );

    } finally {

        sendButton.disabled = false;
        promptInput.focus();
    }
}

function resizeTextarea() {

    promptInput.style.height = "auto";

    promptInput.style.height =
        Math.min(promptInput.scrollHeight, 180) + "px";
}

sendButton.addEventListener("click", sendPrompt);

promptInput.addEventListener("input", resizeTextarea);

promptInput.addEventListener("keydown", event => {

    if (
        event.key === "Enter" &&
        !event.shiftKey
    ) {
        event.preventDefault();
        sendPrompt();
    }
});

exampleButtons.forEach(button => {

    button.addEventListener("click", () => {

        promptInput.value = button.dataset.prompt;

        resizeTextarea();

        promptInput.focus();
    });
});

promptInput.focus();