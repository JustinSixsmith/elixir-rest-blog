import createView from "../createView.js";

const POST_URI = "http://localhost:8080/api/posts";

export default function PostIndex(props) {
    //language=HTML
    return `
        <header>
            <h1>Posts Page</h1>
        </header>
        <main>

            <div class="container-fluid">
                <div>
                    ${props.posts.map(post =>
                            `<h3 id="title-${post.id}">${post.title}</h3>
                             <p id="content-${post.id}">${post.content}</p>
                             <button id="edit-btn-${post.id}" class="btn btn-warning mb-3 edit-btn" data-id="${post.id}">Edit Post</button>
                             <button id="delete-btn-${post.id}" class="btn btn-danger mb-3 delete-btn" data-id="${post.id}">Delete Post</button><hr>`).join('')}
                </div>

                <div id="add-post-container">
                    <form id="add-post-form">
                        <div class="mb-3">
                            <label for="data-id" class="form=label">Post:</label>
                            <input id="post-id" type="text" class="form-control" id="data-id" disabled placeholder="0">
                        </div>
                        <div>
                            <label for="add-post-title" class="form=label">Title</label>
                            <input type="text" class="form-control" id="add-post-title" placeholder="Enter title">
                        </div>
                        <div class="mb-3">
                            <label for="add-post-content" class="form-label">Content</label>
                            <textarea class="form-control" id="add-post-content" rows="3"
                                      placeholder="Enter content"></textarea>
                        </div>
                        <button id="add-post-button" class="btn btn-success mb-3">Add Post</button>
                        <button id="update-post-button" class="btn btn-primary mb-3">Save Post</button>
                    </form>
                </div>
            </div>
        </main>
    `;
}


export function PostEvents() {
    createAddPostListener();
    // TODO: add edit post listener function
    createEditPostListeners();
    // TODO: add delete post listener function
    createDeletePostListeners();
}

function createAddPostListener() {
    console.log("adding a post listener");
    $("#add-post-button").click(function () {
        const title = $("#add-post-title").val();
        const content = $("#add-post-content").val();
        const newPost = {
            title,
            content
        }
        console.log("Ready to add:");
        console.log(newPost);

        const request = {
            method: "Post",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(newPost)
        }
        fetch(POST_URI, request)
            .then(res => {
                console.log(res.status);
                createView("/posts")
            }).catch(error => {
            console.log(error);
        }).finally(() => {
            createView("/posts");
        });
    });
}


function createEditPostListeners() {
    let postId;
    let titleId;
    let contentId;
    let updateTitle;
    let updateContent;
    $(".edit-btn").click(function () {
        console.log("Getting ready to edit post!");
        postId = $(this).data("id");

        titleId = "#title-" + postId;
        updateTitle = $(titleId).text();

        contentId = "#content-" + postId;
        updateContent = $(contentId).text();

        $("#post-id").val(postId);
        $("#add-post-title").val(updateTitle);
        $("#add-post-content").val(updateContent);
    })

    $("#update-post-button").click(function () {
        let newTitle = $("#add-post-title").val();
        let newContent = $("#add-post-content").val();

        const updatedPost = {
            id: postId,
            title: newTitle,
            content: newContent
        }
        console.log("Ready to update:");
        console.log(updatedPost);

        const request = {
            method: "PUT",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(updatedPost)
        }
        fetch(`${POST_URI}/${postId}`, request)
            .then(res => {
                console.log(res.status);
                createView("/posts")
            }).catch(error => {
            console.log(error);
        }).finally(() => {
            createView("/posts");
        });
    });
}


function createDeletePostListeners() {
    $(".delete-btn").click(function () {
        console.log("Getting ready to delete post!");
        const postId = $(this).data("id");

        const request = {
            method: "DELETE",
            headers: {
                'Content-Type': 'application/json',
            }
        };
        fetch(`${POST_URI}/${postId}`, request)
            .then(res => {
                console.log("DELETE SUCCESS: " + res.status);
            }).catch(error => {
            console.log("DELETE ERROR: " + error);
        }).finally(() => {
            createView("/posts");
        })
    });
}