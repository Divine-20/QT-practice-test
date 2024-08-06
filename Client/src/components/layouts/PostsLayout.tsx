import React from "react";
import usePosts from "../../hooks/usePosts";
import { GoTrash } from "react-icons/go";
import { CiEdit } from "react-icons/ci";
import { LiaComments } from "react-icons/lia";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import {
  postId,
  showDeletePostState,
  showAddOrEditPostState,
} from "../../store";
import DeletePostModal from "../modals/DeletePost";
import AddOrEditPostModal from "../modals/AddOrEditPost";
import useComments from "../../hooks/useComments";
import LogoutModal from "./LogoutModal";

function PostsLayout() {
  const { posts } = usePosts();
  console.log("posts", posts);
  const navigate = useNavigate();
  const [, setShowDelete] = useRecoilState(showDeletePostState);
  const [, setShowAddPost] = useRecoilState(showAddOrEditPostState);
  const [, setPost_id] = useRecoilState(postId);
  const { comments } = useComments();

  return (
    <>
      <DeletePostModal />
      <AddOrEditPostModal />
      <div className="flex flex-col items-center justify-center">
        <div>
          <h1 className="text-center font-bold py-4 text-2xl">View posts</h1>
        </div>
        <button
          type="button"
          className="bg-[#354545] py-4 px-4 rounded-lg text-white mt-5"
          onClick={() => setShowAddPost({ show: true, action: "add" })}
        >
          Create New Post
        </button>
        {posts?.map((post, key) => (
          <div
            key={key}
            className="w-1/2 border-[1px] border-[#35454520] hover:border-[#354545] p-4 m-4 flex flex-col gap-4 px-8 rounded-3xl py-6"
          >
            <div className="font-bold flex gap-2">
              <h1 className="italic font-bold">Title: </h1>
              <span>{post.title}</span>
            </div>
            <div className="flex gap-2">
              <h1 className="italic font-bold">Content: </h1>
              <span>{post.content}</span>
            </div>
            <div className="text-xs bg-[#35454520] w-1/4 rounded-full py-2 px-4 flex gap-2">
              <p className="italic text-xs font-bold">Author: </p>
              <span>
                {post.author.firstName} {post.author.lastName}
              </span>
            </div>
            <div className="flex gap-4 justify-end">
              <GoTrash
                onClick={() => {
                  setPost_id(post.id);
                  setShowDelete({
                    show: true,
                    post,
                  });
                }}
                className="text-red-500 cursor-pointer"
              />
              <CiEdit
                className="cursor-pointer"
                onClick={() =>
                  setShowAddPost({ show: true, action: "edit", post })
                }
              />
              <div className="flex items-center">
                <LiaComments
                  onClick={() => {
                    setPost_id(post.id);
                    navigate("/comments");
                  }}
                  className="cursor-pointer"
                />
              </div>
            </div>
          </div>
        ))}
        <LogoutModal />
      </div>
    </>
  );
}

export default PostsLayout;
