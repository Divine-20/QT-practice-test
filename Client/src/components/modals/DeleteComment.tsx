import { Button } from "@mantine/core";
import { useRecoilState } from "recoil";
import ModalLayout from "../layouts/ModalLayout";
import { showDeleteCommentState, showDeletePostState } from "../../store";
import useComments from "../../hooks/useComments";

export default function DeleteCommentModal() {
  const [show, setShow] = useRecoilState(showDeleteCommentState);
  const { deleteComment, deletingComment } = useComments();

  return (
    <ModalLayout open={Boolean(show?.show)} onClose={() => setShow(null)}>
      <h1 className="text-2xl font-semibold pb-4">Delete Post</h1>
      <p className="pb-4">Are you sure you want to delete your comment?</p>
      <div className="flex w-full justify-end gap-4 pt-5">
        <Button onClick={() => setShow(null)} variant="outline" color="gray">
          Cancel
        </Button>
        <Button
          onClick={() => deleteComment(show!.comment!.id)}
          loading={deletingComment}
          disabled={deletingComment}
          color="red"
        >
          Delete
        </Button>
      </div>
    </ModalLayout>
  );
}
