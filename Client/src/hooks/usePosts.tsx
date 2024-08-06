/* eslint-disable react-hooks/exhaustive-deps */
import { notifications } from "@mantine/notifications";
import { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import useSWR from "swr";
import axios from "../lib/axios.config";
import { postId, showAddOrEditPostState, showDeletePostState } from "../store";
import { Post } from "../types";
import useAuth from "./useAuth";

export default function usePosts() {
  const { user } = useAuth();
  const [creatingPost, setCreatingPost] = useState(false);
  const [deletingPost, setDeletingPost] = useState(false);
  const [updatingPost, setUpdatingPost] = useState(false);
  const [, setShowAddOrEdit] = useRecoilState(showAddOrEditPostState);
  const [, setShowDelete] = useRecoilState(showDeletePostState);
  const {
    data: posts,
    isLoading,
    error,
    mutate,
  } = useSWR<Post[]>("/posts/all", async (url: string) => {
    const { data } = await axios.get(url);
    return data.data;
  });

  useEffect(() => {
    mutate();
  }, [user]);

  const createPost = async (post: Omit<Post, "id" | "createdAt">) => {
    setCreatingPost(true);
    try {
      const { data } = await axios.post("/posts/create", post);
      if (data.success) {
        notifications.show({
          title: "Success",
          message: "Post created successfully",
          color: "green",
        });
        mutate([...(posts || []), data.post]);
        setShowAddOrEdit(null);
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
      setCreatingPost(false);
    }
  };

  const deletePost = async (id: string) => {
    setDeletingPost(true);
    try {
      const { data } = await axios.delete(`/posts/delete/${id}`);
      if (data.success) {
        notifications.show({
          title: "Success",
          message: "Post deleted successfully",
          color: "green",
        });
        mutate(posts?.filter((post) => post.id !== id));
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
      setDeletingPost(false);
    }
  };

  const updatePost = async (post: Post) => {
    setUpdatingPost(true);
    try {
      const { data } = await axios.put(`/posts/update/${post.id}`, post);
      if (data.success) {
        notifications.show({
          title: "Success",
          message: "Post updated successfully",
          color: "green",
        });
        mutate(posts?.map((p) => (p.id === post.id ? post : p)));
        setShowAddOrEdit(null);
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
      setUpdatingPost(false);
    }
  };

  return {
    posts,
    isLoading,
    error,
    createPost,
    deletePost,
    updatePost,
    creatingPost,
    deletingPost,
    updatingPost,
  };
}
