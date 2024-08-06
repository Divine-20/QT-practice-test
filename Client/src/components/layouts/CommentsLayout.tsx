import React from "react";
import useComments from "../../hooks/useComments";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import {
  showDeleteCommentState,
  postId,
  showAddOrEditCommentState,
} from "../../store";
import { GoTrash } from "react-icons/go";
import { CiEdit } from "react-icons/ci";
import DeleteCommentModal from "../modals/DeleteComment";
import AddOrEditCommentModal from "../modals/AddOrEditComment";
import LogoutModal from "./LogoutModal";

function CommentsLayout() {
  const { comments } = useComments();
  console.log("comments", comments);
  const navigate = useNavigate();
  const [, setShowDelete] = useRecoilState(showDeleteCommentState);
  const [, setShowAddComment] = useRecoilState(showAddOrEditCommentState);
  const [currentPostId] = useRecoilState(postId);

  return (
    <>
      <DeleteCommentModal />
      <AddOrEditCommentModal />
      <div className="flex flex-col items-center justify-center">
        <div>
          <h1 className="text-center font-bold py-4 text-2xl">View Comments</h1>
        </div>
        <button
          type="button"
          className="bg-[#354545] py-4 px-4 rounded-lg text-white mt-5"
          onClick={() => setShowAddComment({ show: true, action: "add" })}
        >
          Create New Comment
        </button>
        {comments?.map((comment: any, key: any) => (
          <div
            key={key}
            className="w-1/2 border-[1px] border-[#35454520] hover:border-[#354545] p-4 m-4 flex flex-col gap-4 px-8 rounded-3xl py-6"
          >
            <div className="flex gap-2">
              <h1 className="italic font-bold">Content: </h1>
              {comment.content}
            </div>
            <div className="text-xs bg-[#35454520] w-1/4 flex gap-2 rounded-full py-2 px-4">
              <p className="italic text-xs font-bold">Author: </p>
              {comment.author.firstName} {comment.author.lastName}
            </div>
            <div className="flex gap-4 justify-end">
              <GoTrash
                onClick={() =>
                  setShowDelete({
                    show: true,
                    comment,
                  })
                }
                className="text-red-500 cursor-pointer"
              />
              <CiEdit
                className="cursor-pointe"
                onClick={() => {
                  setShowAddComment({
                    show: true,
                    action: "edit",
                    comment,
                  });
                }}
              />
            </div>
          </div>
        ))}
        <LogoutModal />
      </div>
    </>
  );
}

export default CommentsLayout;
