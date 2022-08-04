<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/head_withEditor.jspf"%>


<script>
function ArticleSave__submitForm(form){

    form.title.value= form.title.value.trim();
    if(form.title.value.length==0) {
        alert('내용을 입력해주세요');
        form.title.focus();
        return;
    }
    const editor = $(form).find('.toast-ui-editor').data("data-toast-editor");
    console.log(editor);
    const markdown = editor.getMarkdown();
    form.body.value=markdown.trim();
    console.log(form.body.value);
    //form.body.value = form.body.value.trim();
    if(form.body.value.length==0) {
            alert('내용을 입력해주세요');
            form.body.focus();
            return;
        }
/*    form.writer.value = form.writer.value.trim();
    if(form.writer.value.length==0) {
            alert('내용을 입력해주세요');
            form.writer.focus();
            return;
        }*/
    form.submit();
}
</script>

<div>
    <div class="md:grid md:grid-cols-6 md:gap-6">
        <div class="md:col-span-1">
            <div class="mx-4 mt-4 text-center px-4 sm:px-0">
                <h3 class="text-lg font-medium leading-6 text-gray-900">Article</h3>
                <p class="mt-1 text-sm text-gray-600">
                    게시글 작성
                </p>
            </div>
        </div>
        <div class="mt-5 md:mt-0 md:col-span-4">


            <form method="POST" onsubmit="ArticleSave__submitForm(this); return false;">
                <div class="shadow sm:rounded-md sm:overflow-hidden">
                    <div class="px-4 py-5 bg-white space-y-6 sm:p-6">
                        <div class="grid grid-cols-3 gap-6">
                            <div class="col-span-3 sm:col-span-2">
                                <label for="title" class="block text-sm font-large  text-gray-700">
                                    제목
                                </label>
                                <div class="mt-1 flex rounded-md shadow-sm">
                                    <input type="text" name="title" id="title" class="focus:ring-indigo-500 focus:border-indigo-500 flex-1 block w-full rounded-none rounded-r-md sm:text-sm border-gray-300" placeholder="제목을 입력하세요">
                                </div>
                            </div>
                        </div>

                        <div>
                            <label for="body" class="block text-sm font-large text-gray-700">
                                내용
                            </label>

                          <div class="toast-ui-editor" toast-ui-editor--height="800px"></div>
                                <!-- 숨긴다!! -> 토스트 에디터로 받기 -->
                            <div class="mt-1"> <input type="hidden" id="body" name="body"></input> </div>
                        </div>
                        <!--div>
                            <label for="writer" class="block text-sm font-medium text-gray-700">
                                작성자
                            </label>
                            <div class="mt-1">
                                <textarea id="writer" name="writer" rows="3" class="shadow-sm focus:ring-indigo-500 focus:border-indigo-500 mt-1 block w-full sm:text-sm border-gray-300 rounded-md" placeholder="작성자을 입력하세요."></textarea>
                            </div>
                        </div-->
                        <!--div> <div class="mt-1 flex items-center">
                                <button type="button" class="ml-5 bg-white py-2 px-3 border border-gray-300 rounded-md shadow-sm text-sm leading-4 font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                                    버튼 예시 - 가지고 있자
                                </button>
                            </div>
                        </div-->

                    </div>
                    <div class="px-4 py-3 bg-gray-50 text-right sm:px-6">

                        <button type="submit" class="inline-flex justify-center py-2 px-4 border border-transparent shadow-sm text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
                            Save
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>




<%@ include file="../common/foot.jspf"%>