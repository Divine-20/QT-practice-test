import { atom } from "recoil";
import { Comment, Post } from "../types";

export const showAddOrEditPostState = atom<{
  show: boolean;
  action: "add" | "edit";
  post?: Post;
} | null>({
  key: "ShowAddPost",
  default: null,
});

export const showDeletePostState = atom<{
  show: boolean;
  post?: Post;
} | null>({
  key: "ShowDeletePost",
  default: null,
});

export const showAddOrEditCommentState = atom<{
  show: boolean;
  action: "add" | "edit";
  comment?: Comment;
} | null>({
  key: "ShowAddComment",
  default: null,
});

export const showDeleteCommentState = atom<{
  show: boolean;
  comment?: Comment;
} | null>({
  key: "ShowDeleteComment",
  default: null,
});
export const postId = atom<any>({
  key: "postId",
  default: "",
});
