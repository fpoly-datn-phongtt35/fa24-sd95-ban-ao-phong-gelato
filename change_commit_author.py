import git

def update_commit_author(repo_path, old_name, new_name, new_email):
    # Mở repository Git
    repo = git.Repo(repo_path)
    
    # Lặp qua tất cả các commit
    for commit in repo.iter_commits('--all'):
        # Nếu tên tác giả của commit là old_name, ta sẽ thay đổi nó
        if commit.author.name == old_name:
            print(f"Updating commit: {commit.hexsha}")
            # Thay đổi tên và email tác giả và committer
            commit.author.name = vanthai04
            commit.author.email = thaitvph40872@fpt.edu.vn
            commit.committer.name = vanthai04
            commit.committer.email = thaitvph40872@fpt.edu.vn
            
            # Sửa lại commit
            commit.repo.git.commit(amend=True, author=f"{commit.author.name} <{commit.author.email}>")

    # Sau khi thay đổi, force push lên repository từ xa
    repo.git.push("--force")
    print("Changes pushed to remote repository.")

if __name__ == "__main__":
    # Đường dẫn tới repository của bạn
    repo_path = "/path/to/your/repo"  # Thay đổi đường dẫn đến repo của bạn
    
    # Tên và email cũ, và tên và email mới
    old_name = "quangrambo"  # Tên cũ mà bạn muốn thay
    new_name = "Tên của bạn"  # Tên mới của bạn
    new_email = "email@moi.com"  # Email mới của bạn

    # Gọi hàm để thay đổi commit
    update_commit_author(repo_path, old_name, new_name, new_email)
