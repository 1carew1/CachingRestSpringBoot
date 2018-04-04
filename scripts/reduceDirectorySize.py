import os
from os import walk
import sys


def populate_file_list(dirpath):
    file_list = []
    for (directory, dirnames, filenames) in walk(dirpath):
        for filename in filenames:
            file_list.append(directory + "/" + filename)
    return file_list


def delete_unwated_files(file_list, max_dir_size_bytes):
    amount_deleted = 0
    total_file_size = 0
    for file_path in file_list:
        file_size_bytes = os.stat(file_path).st_size
        if (total_file_size + file_size_bytes > max_dir_size_bytes):
            os.remove(file_path)
            amount_deleted = amount_deleted + 1
        else:
            total_file_size = total_file_size + file_size_bytes
    print('Number of Files Deleted :', amount_deleted)
    print('Dir Size :', total_file_size / (1024 * 1024), " MB")


if (__name__ == "__main__"):
    dirpath = sys.argv[1]
    if (os.path.exists(dirpath) and os.path.isdir(dirpath)):
        max_dir_size_bytes = 4 * 1024 * 1024 * 1024  # 4GB
        print('Reducing Directory until it is ', max_dir_size_bytes / (1024 * 1024), " MB")
        file_list = populate_file_list(dirpath)
        print("There are ", len(file_list), " files in total")
        delete_unwated_files(file_list, max_dir_size_bytes)
        file_list = populate_file_list(dirpath)
        print("There are now ", len(file_list), " files remaining")
