import { Button } from "@mantine/core";
import { useRecoilState } from "recoil";
import ModalLayout from "../layouts/ModalLayout";
import usePosts from "../../hooks/usePosts";
import { showDeletePostState } from "../../store";

export default function DeletePostModal() {
  const [show, setShow] = useRecoilState(showDeletePostState);
  const { deletePost, deletingPost } = usePosts();

  return (
    <ModalLayout open={Boolean(show?.show)} onClose={() => setShow(null)}>
      <h1 className="text-2xl font-semibold pb-4">Delete Post</h1>
      <p className="pb-4">
        Are you sure you want to delete {show?.post?.title}?
      </p>
      <div className="flex w-full justify-end gap-4 pt-5">
        <Button onClick={() => setShow(null)} variant="outline" color="gray">
          Cancel
        </Button>
        <Button
          onClick={() => deletePost(show!.post!.id)}
          loading={deletingPost}
          disabled={deletingPost}
          color="red"
        >
          Delete
        </Button>
      </div>
    </ModalLayout>
  );
}
