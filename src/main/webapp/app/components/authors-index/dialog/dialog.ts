import {IAuthor, IAuthorsAndCountPages, IAuthorsApi} from "../../../services/authors-api/authors-api";
import {ListParams} from "../../../services/service-api";

export class DialogChange {
    private clickSave: boolean = false;
    private author: IAuthor = new IAuthor();
    private modalOptions = {
        closeButtonText: 'CANCEL',
        actionButtonText: 'SAVE',
        headerText: `Add new author`,
    };
    constructor(private $uibModalInstance: ng.ui.bootstrap.IModalServiceInstance,
                private authorsApi: IAuthorsApi,
                private authorsAndCountPages: IAuthorsAndCountPages,
                private currentPage: number,
                private listParams: ListParams<IAuthor>) {
    }

    private reloadDates(): void {
        this.authorsApi.find(this.listParams)
            .then(authorsAndCountPages => {
                this.authorsAndCountPages = authorsAndCountPages;
                this.authorsAndCountPages.list.forEach(author => author.averageRatingRound = Math.round(author.averageRating))
            });
    }

    private save(firstName, secondName): void {
        this.author.firstName = firstName;
        this.author.secondName = secondName;
        this.authorsApi.create(this.author).then(response => this.reloadDates());
        this.$uibModalInstance.close(this.authorsAndCountPages);
    }

    private cancel(): void {
        this.$uibModalInstance.close();
    }
}