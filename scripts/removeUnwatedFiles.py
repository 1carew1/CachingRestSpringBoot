import os
from os import walk
import sys


def populate_file_list(dirpath):
    file_list = []
    for(directory, dirnames, filenames) in walk(dirpath):
        for filename in filenames:
            file_list.append(directory + "/" + filename)
    return file_list

def delete_unwated_files(file_list):
    amount_deleted = 0
    for file_path in file_list :
        lower_case_file_path = file_path.lower()
        file_size_kb = os.stat(file_path).st_size / 1024
        if (not ((lower_case_file_path.endswith('.jpeg')) or (lower_case_file_path.endswith('.jpg')) or (lower_case_file_path.endswith('.png')))) or (file_size_kb > 900):
            os.remove(file_path)
            amount_deleted = amount_deleted + 1
    print('Number of Files Deleted :', amount_deleted)


if(__name__ == "__main__"):
    dirpath = sys.argv[1]
    if(os.path.exists(dirpath) and os.path.isdir(dirpath)):
        print('Removing files that are not JPEGs or PNGs. Also removing files that are over 900kB')
        file_list = populate_file_list(dirpath)
        print("There are ", len(file_list), " files in total")
        delete_unwated_files(file_list)
