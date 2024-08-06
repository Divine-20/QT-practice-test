import React, { useEffect } from "react";
import ModalLayout from "../layouts/ModalLayout";
import { useRecoilState } from "recoil";
import { postId, showAddOrEditPostState } from "../../store";
import { Input, Button, Textarea } from "@mantine/core";
import usePosts from "../../hooks/usePosts";

export default function AddOrEditPostModal() {
  const [show, setShow] = useRecoilState(showAddOrEditPostState);
  const { createPost, creatingPost, updatePost, updatingPost } = usePosts();
  useEffect(() => {
    if (show) {
      console.log("Show state:", show);
    }
  }, [show]);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const data = {
      title: formData.get("title") as string,
      content: formData.get("content") as string,
      author: formData.get("author") as string,
    };

    if (show?.action === "add") {
      createPost(data);
    } else if (show?.action === "edit" && show.post) {
      updatePost({
        ...show.post,
        ...data,
      });
    }
  };

  const handleClose = () => {
    setShow(null);
  };

  return (
    <ModalLayout open={Boolean(show?.show)} onClose={handleClose}>
      <h1 className="text-2xl font-semibold pb-4 capitalize">
        {show?.action} Post
      </h1>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div className="grid gap-2">
          <label htmlFor="title">Post Title</label>
          <Input
            id="title"
            required
            name="title"
            className=""
            placeholder="Post Title"
            defaultValue={show?.post?.title}
          />
        </div>
        <div className="grid gap-2">
          <label htmlFor="author">Author</label>
          <Input
            id="author"
            required
            name="author"
            className="focus:ring-[#354548]"
            placeholder="Author"
            defaultValue={
              show?.post?.author?.firstName + " " + show?.post?.author?.lastName
            }
          />
        </div>
        <div className="grid gap-2">
          <label htmlFor="content">Post Content</label>
          <Textarea
            id="content"
            required
            name="content"
            className="focus:ring-inset focus:ring-[#354548]"
            placeholder="Post Content"
            defaultValue={show?.post?.content}
          />
        </div>
        <div className="flex w-full justify-end gap-4 pt-5">
          <Button onClick={handleClose} variant="outline" color="gray">
            Cancel
          </Button>
          <Button
            type="submit"
            loading={creatingPost || updatingPost}
            disabled={creatingPost || updatingPost}
            className="bg-[#354545]  hover:bg-[#35454580]"
          >
            {show?.action === "add" ? "Add Post" : "Update Post"}
          </Button>
        </div>
      </form>
    </ModalLayout>
  );
}
