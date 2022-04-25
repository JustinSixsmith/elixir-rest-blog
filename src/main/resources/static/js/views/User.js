import CreateView from "../createView.js"
import {getHeaders} from "../auth.js";

const BASE_URI = "http://localhost:8080/api/user";

export default function User(props) {
    //language=HTML
    return `
        <header>
            <h1>User Page</h1>
        </header>
        <main>
            <div class="container-fluid">
                <div>
                    <p>
                    <h3>${props.user.username}</h3>
                    <form class="form-inline">
                        <div class="form-group mb-2">
                            <label for="staticEmail2" class="sr-only">Email:</label>
                            <input type="text" readonly class="form-control-plaintext" id="staticEmail2"
                                   value="${props.user.email}">
                        </div>
                        <div class="form-group mx-sm-3 mb-2">
                            <label for="old-pw" class="sr-only">Old Password:</label>
                            <input type="password" class="form-control" id="old-pw" value="${props.user.password}"
                                   disabled>
                            <input type="password" class="form-control" id="confirm-old-pw"
                                   placeholder="Enter Old Password">
                        </div>
                        <div class="form-group mx-sm-3 mb-2">
                            <label for="new-pw" class="sr-only">New Password:</label>
                            <input type="password" class="form-control" id="new-pw" placeholder="Enter New Password">
                            <input type="password" class="form-control" id="confirm-new-pw" placeholder="Confirm Password">
                        </div>
                        <button id="change-pw-btn" type="submit" class="btn btn-primary mb-2">Change Password</button>
                    </form>
                    <div>
                        ${props.user.posts.map(post =>
                                `<h3 id="title-${post.id}">${post.title}</h3>
                             <p id="content-${post.id}">${post.content}</p>`).join('')}
                    </div>
                    </p>
                </div>
            </div>
        </main>
    `;
}

export function UserEvents() {
    $("#change-pw-btn").click(function() {
        // 1. grab data from form fields
        const userId = 1; // $("#add-post-id").val();
        let uriExtra = '/1/updatePassword';
        // const oldPassword = $("#old-pw").val();
        const newPassword = $("#new-pw").val();

        // 2. assemble the request
        const request = {
            headers: getHeaders(),
            method: "PUT"
        }

        // 3. do the fetch with the correct URI please (check against Postman)
        fetch(`${BASE_URI}${uriExtra}?newPassword=${newPassword}`, request)
            .then(res => {
                console.log(`${request.method} SUCCESS: ${res.status}`);
            }).catch(error => {
            console.log(`${request.method} ERROR: ${error}`);
        }).finally(() => {
            createView("/users");
        });

    });
}