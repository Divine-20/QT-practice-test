import React from "react";
import ModalLayout from "../layouts/ModalLayout";
import { useRecoilState } from "recoil";
import { showAddOrEditCommentState } from "../../store";
import { Input, Button, Textarea } from "@mantine/core";
import useComments from "../../hooks/useComments";

export default function AddOrEditCommentModal() {
  const [show, setShow] = useRecoilState(showAddOrEditCommentState);
  const { createComment, creatingComment, updateComment, updatingComment } =
    useComments();

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const data = {
      content: formData.get("content") as string,
      author: formData.get("author") as string,
    };

    if (show?.action === "add") {
      createComment(data);
    } else if (show?.action === "edit" && show.comment) {
      updateComment({
        ...show.comment,
        ...data,
      });
    }
  };
  return (
    <ModalLayout open={Boolean(show?.show)} onClose={() => setShow(null)}>
      <h1 className="text-2xl font-semibold pb-4 capitalize">
        {show?.action} Comment
      </h1>
      <form onSubmit={handleSubmit} className="space-y-4">
        <div className="grid gap-2">
          <label htmlFor="title">Author</label>
          <Input
            id="author"
            required
            min={2}
            name="author"
            placeholder="Author "
            className="focus:ring-[#354548]"
            defaultValue={
              show?.comment?.author?.firstName +
              " " +
              show?.comment?.author?.lastName
            }
          />
        </div>
        <div className="grid gap-2">
          <label htmlFor="content">Comment Content</label>
          <Textarea
            required
            id=""
            name="content"
            className="focus:ring-[#354548]"
            placeholder="Post Content"
            defaultValue={show?.comment?.content}
          />
        </div>

        <div className="flex w-full justify-end gap-4 pt-5">
          <Button onClick={() => setShow(null)} variant="outline" color="gray">
            Cancel
          </Button>
          <Button
            type="submit"
            loading={creatingComment || updatingComment}
            disabled={creatingComment || updatingComment}
            className="bg-[#354545]  hover:bg-[#35454580]"
          >
            {show?.action === "add" ? "Add Comment" : "Update Comment"}
          </Button>
        </div>
      </form>
    </ModalLayout>
  );
}
