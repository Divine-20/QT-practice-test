/* eslint-disable react-hooks/exhaustive-deps */
import { notifications } from "@mantine/notifications";
import { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import useSWR from "swr";
import axios from "../lib/axios.config";
import {
  showAddOrEditCommentState,
  showDeleteCommentState,
  postId,
} from "../store";
import { Comment } from "../types";
import useAuth from "./useAuth";
import { useNavigate } from "react-router-dom";

export default function useComments() {
  const { user } = useAuth();
  const [creatingComment, setCreatingComment] = useState(false);
  const [deletingComment, setDeletingComment] = useState(false);
  const [updatingComment, setUpdatingComment] = useState(false);
  const [, setShowAddOrEdit] = useRecoilState(showAddOrEditCommentState);
  const [, setShowDelete] = useRecoilState(showDeleteCommentState);
  const [currentPostId] = useRecoilState(postId);
  const navigate = useNavigate();
  const fetcher = async (url: string) => {
    const { data } = await axios.get(url);
    return data.data;
  };

  const {
    data: comments,
    isLoading,
    error,
    mutate,
  } = useSWR<Comment[]>(
    currentPostId ? `/posts/comments/${currentPostId}` : null,
    fetcher
  );

  useEffect(() => {
    if (user) {
      mutate();
    }
  }, [user, currentPostId]);

  const createComment = async (comment: Omit<Comment, "id" | "createdAt">) => {
    setCreatingComment(true);
    try {
      const { data } = await axios.post(
        `/comments/create/${currentPostId}`,
        comment
      );
      if (data.success) {
        notifications.show({
          title: "Success",
          message: "Comment created successfully",
          color: "green",
        });
        mutate([...(comments || []), data.comment]);
        setShowAddOrEdit(null);
        navigate("/comments");
      } else {
        notifications.show({
          title: "Error",
          message: "An error occurred",
          color: "red",
        });
      }
    } catch (error) {
      console.error(error);
      notifications.show({
        title: "Error",
        message: "An error occurred",
        color: "red",
      });
    } finally {
      setCreatingComment(false);
    }
  };

  const deleteComment = async (id: string) => {
    setDeletingComment(true);
    try {
      const { data } = await axios.delete(`/comments/delete/${id}`);
      if (data.success) {
        notifications.show({
          title: "Success",
          message: "Comment deleted successfully",
          color: "green",
        });
        mutate(comments?.filter((comment) => comment.id !== id));
        setShowDelete(null);
      } else {
        notifications.show({
          title: "Error",
          message: "An error occurred",
          color: "red",
        });
      }
    } catch (error) {
      console.error(error);
      notifications.show({
        title: "Error",
        message: "An error occurred",
        color: "red",
      });
    } finally {
      setDeletingComment(false);
    }
  };

  const updateComment = async (comment: Comment) => {
    setUpdatingComment(true);
    try {
      const { data } = await axios.put(
        `/comments/update/${comment.id}`,
        comment
      );
      if (data.success) {
        notifications.show({
          title: "Success",
          message: "Comment updated successfully",
          color: "green",
        });
        mutate(comments?.map((p) => (p.id === comment.id ? comment : p)));
        setShowAddOrEdit(null);
        navigate("/comments");
      } else {
        notifications.show({
          title: "Error",
          message: "An error occurred",
          color: "red",
        });
      }
    } catch (error) {
      console.error(error);
      notifications.show({
        title: "Error",
        message: "An error occurred",
        color: "red",
      });
    } finally {
      setUpdatingComment(false);
    }
  };

  return {
    comments,
    isLoading,
    error,
    createComment,
    deleteComment,
    updateComment,
    creatingComment,
    deletingComment,
    updatingComment,
  };
}
