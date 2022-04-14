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
                    ${props.posts.map(post => `<h3>${post.title}</h3><p>${post.content}</p><button id="${post.id}" class="btn btn-secondary mb-3 edit-btn">Edit Post</button>
                        <button id="${post.id}" class="btn btn-danger mb-3 delete-btn">Delete Post</button>`).join('')}
                </div>

                <div id="add-post-container">
                    <form id="add-post-form">
                        <!--                        <div class="mb-3">-->
                        <!--                            <label for="data-id" class="form=label">Post #</label>-->
                        <!--                            <input type="text" class="form-control" id="data-id" disabled>-->
                        <!--                        </div>-->
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


export function PostsEvent() {
    createAddPostListener();
    // TODO: add edit post listener function
    createEditPostListener();
    // TODO: add delete post listener function
    createDeletePostListener();
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


function createEditPostListener(postId) {
    $(".edit-btn").click(function () {
        console.log("Getting ready to edit post!");
        const id = this.id;
        console.log(id);

    })
}


function createDeletePostListener() {
    $(".delete-btn").click(function () {
        console.log("Getting ready to delete post!");
        const id = this.id;
        console.log(id);
    })
}